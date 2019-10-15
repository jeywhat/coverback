package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.model.Game;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class GameSearchService {

    @Value("${storage.location}")
    private String storageLocation;

    private List<Game> gameList = new ArrayList<>();

    @PostConstruct
    private void init(){
        this.searchGames();
    }

    private void displayDirectoryContents(File dir) {
        try {
            File[] files = dir.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    displayDirectoryContents(file);
                } else {
                    if(file.toString().endsWith(".xci") || file.toString().endsWith(".nsp")){
                        gameList.add(new Game(file.getAbsolutePath(), file.getName().replaceFirst("[.][^.]+$", ""), getFileExtension(file) ,getFileSizeMegaBytes(file)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }


    public List<Game> getGameList() {
        return gameList;
    }

    public void searchGames() {
        if(!gameList.isEmpty()){
            gameList.clear();
        }
        File currentDir = new File(this.storageLocation);
        displayDirectoryContents(currentDir);
    }

    private BigDecimal getFileSizeMegaBytes(File file) {
        return new BigDecimal((double) file.length() / (1024 * 1024)).setScale(2, RoundingMode.HALF_UP);
    }
}
