package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.helper.GameHelper;
import fr.jeywhat.coverback.model.Game;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@Component
@Getter
public class GameSearchService {

    @Value("${storage.location}")
    private String storageLocation;

    @Value("#{'${supported.extensions.files}'.split(',')}")
    private List<String> supportedExtensionFiles;

    @Value("#{'${ignored.prefix.files}'.split(',')}")
    private List<String> ignoredPrefixFiles;

    private CoverService coverService;

    public GameSearchService(CoverService coverService){
        this.coverService = coverService;
    }

    @PostConstruct
    private void init(){
        new Thread(this::searchGames).start();
    }

    public void searchGames() {
        File currentDir = new File(this.storageLocation);
        displayDirectoryContents(currentDir);
    }

    private void displayDirectoryContents(File dir) {
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                displayDirectoryContents(file);
            } else {
                if(GameHelper.isSupportedFile(file, supportedExtensionFiles, ignoredPrefixFiles)){
                    coverService.addGame(new Game(file));
                }
            }
        }
    }

}
