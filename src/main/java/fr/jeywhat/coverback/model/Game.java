package fr.jeywhat.coverback.model;

import lombok.Data;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Game {

    private String fullpath;

    private String name;

    private String extension;

    private BigDecimal size;

    public Game(File file){
        this.fullpath = file.getAbsolutePath();
        this.name = file.getName().replaceFirst("[.][^.]+$", "");
        this.extension = getFileExtension(file);
        this.size = getFileSizeMegaBytes(file);
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    private BigDecimal getFileSizeMegaBytes(File file) {
        return new BigDecimal((double) file.length() / (1024 * 1024)).setScale(2, RoundingMode.HALF_UP);
    }

}
