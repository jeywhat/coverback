package fr.jeywhat.coverback.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GameInformation {

    private String title;

    private String releaseDate = "?";

    private String description = "?";

    private ArrayList<String> genre = new ArrayList<>();

    private String image;

    private Integer score = 0;

    private String developer = "?";

    private ArrayList<String> publisher = new ArrayList<>();

    private String rating = "?";

    private ArrayList<String> alsoAvailableOn = new ArrayList<>();

}
