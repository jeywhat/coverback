package fr.jeywhat.coverback.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

@Service
public class DirectoryWatcher {

    @Value("${storage.location}")
    private String storageLocation;

    private static final Logger logger = LoggerFactory.getLogger(DirectoryWatcher.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @PostConstruct
    private void init() {
        executeAsynchronously();
    }

    public void executeAsynchronously() {
        taskExecutor.execute(() -> {
            // get path object pointing to the directory we wish to monitor
            Path path = Paths.get(storageLocation);
            try {
                // get watch service which will monitor the directory
                WatchService watcher = path.getFileSystem().newWatchService();
                // associate watch service with the directory to listen to the event
                // types
                path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
                logger.info("Monitoring directory for changes...");
                // listen to events

                WatchKey watchKey = watcher.take();
                // get list of events as they occur
                List<WatchEvent<?>> events = watchKey.pollEvents();
                //iterate over events
                for (WatchEvent event : events) {
                    //check if the event refers to a new file created
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        //print file name which is newly created
                        logger.info("Created: " + event.context().toString());
                    } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        logger.warn("Deleted: " + event.context().toString());
                    }
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
