package fr.jeywhat.coverback.model;

import lombok.Data;

@Data
public class Games {

    private String fullpath;

    private String name;

    private String extension;

    private String size;

    public Games(String fullpath, String name, String extension, String size){
        this.fullpath = fullpath;
        this.name = name;
        this.extension = extension;
        this.size = size;
    }


}
