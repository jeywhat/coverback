package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.helper.GameHelper;
import fr.jeywhat.coverback.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

@Service
public class DirectoryWatcher {

    @Value("${storage.location}")
    private String storageLocation;

    private static final Logger logger = LoggerFactory.getLogger(DirectoryWatcher.class);

    private TaskExecutor taskExecutor;

    private CoverService coverService;

    public DirectoryWatcher(TaskExecutor taskExecutor, CoverService coverService){
        this.taskExecutor = taskExecutor;
        this.coverService = coverService;
    }

    @PostConstruct
    private void init() {
        File file = new File(storageLocation);
        executeAsynchronously(storageLocation);
        displayDirectoryContents(file);
    }

    public void executeAsynchronously(String dirPath) {
        taskExecutor.execute(() -> {
            File file = new File(dirPath);
            Path path = Paths.get(dirPath);

            try {
                WatchService watchService = path.getFileSystem().newWatchService();
                path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

                logger.info("Start watching new directory : " + dirPath);

                while (file.exists()) {
                    WatchKey watchKey = watchService.take();
                    for (final WatchEvent<?> event : watchKey.pollEvents()) {

                        String nameFile = event.context().toString();
                        File gameFile = new File(dirPath + "\\" +  nameFile);

                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            logger.info("Created: " + nameFile);
                            createdNewFile(gameFile);

                        } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                            logger.warn("Deleted: " + nameFile);
                            deletedCurrentFile(gameFile);
                        }
                    }
                    watchKey.reset();
                }

                logger.info("Stop watching directory : " + dirPath);

            } catch (InterruptedException interruptedException) {
                logger.error("Thread got interrupted:" + interruptedException);
            } catch (Exception exception) {
                logger.error(exception.toString());
            }
        });
    }

    private void createdNewFile(File file){
        if(file.isDirectory()){
            executeAsynchronously(file.toString());
        }else if(GameHelper.isSwitchGame(file)){
            coverService.addGame(new Game(file));
        }else{
            logger.warn("Skipped file : " + file.toString());
        }
    }

    private void deletedCurrentFile(File file){
        if(!file.isDirectory()){
            if(GameHelper.isSwitchGame(file)){
                coverService.removeGame(new Game(file).getName());
            }else{
                logger.warn("Skipped file : " + file.toString());
            }
        }
    }

    private void displayDirectoryContents(File dir) {
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                executeAsynchronously(file.getAbsolutePath());
                displayDirectoryContents(file);
            }
        }
    }


}
