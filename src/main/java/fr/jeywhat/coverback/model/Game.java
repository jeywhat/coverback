package fr.jeywhat.coverback.model;

import lombok.Data;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

@Data
public class Game {

    private final String SPLITTER_PATTERN = "_";

    private String fullpath;

    private String name;

    private boolean isSuperXCI = false;

    private int nbDLC = 0;

    private String version = "v0";

    private String extension;

    private BigDecimal size;

    public Game(String fullpath){
        this(new File(fullpath));
    }

    public Game(File file){
        this.fullpath = file.getAbsolutePath();
        try {
            //Fix for SynologyOS
            String[] fileNameParser = file.getName().split("\\\\");
            parserGameName(fileNameParser[fileNameParser.length-1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void parserGameName(String name) {
        String[] nameSplitted = name.replaceFirst("[.][^.]+$", "").split(SPLITTER_PATTERN);
        this.name = nameSplitted[0];
        if(nameSplitted.length >= 4){
            this.isSuperXCI = "SuperXCI".equals(nameSplitted[1]);
            this.nbDLC = Integer.parseInt(nameSplitted[2]);
            this.version = nameSplitted[3];
        }

    }

}
