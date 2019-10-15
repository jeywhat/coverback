package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.helper.GameHelper;
import fr.jeywhat.coverback.model.Game;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
@Getter
public class GameSearchService {

    @Value("${storage.location}")
    private String storageLocation;

    private CoverService coverService;

    public GameSearchService(CoverService coverService){
        this.coverService = coverService;
    }

    @PostConstruct
    private void init(){
        this.searchGames();
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
                if(GameHelper.isSwitchGame(file)){
                    coverService.addGame(new Game(file));
                }
            }
        }
    }

}
