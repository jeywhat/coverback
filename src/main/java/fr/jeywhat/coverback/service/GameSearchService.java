package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.model.Games;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameSearchService {

    private List<Games> gamesList = new ArrayList<>();

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
                        gamesList.add(new Games(file.getAbsolutePath(), file.getName().replaceFirst("[.][^.]+$", ""), getFileExtension(file) ,getFileSizeMegaBytes(file)));
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


    public List<Games> getGamesList() {
        return gamesList;
    }

    public void searchGames() {
        if(!gamesList.isEmpty()){
            gamesList.clear();
        }
        File currentDir = new File("\\\\192.168.1.25\\Switch\\Jeux");
        displayDirectoryContents(currentDir);
    }

    private String getFileSizeMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024) + " mb";
    }
}
