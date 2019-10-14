package fr.jeywhat.coverback.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class GameInformation {

    private String title;
    private String releaseDate;
    private String description;
    private ArrayList<String> genre = new ArrayList<>();
    private String image;
    private Integer score;
    private String developer;
    private ArrayList<String> publisher = new ArrayList<>();
    private String rating;
    private ArrayList<String> alsoAvailableOn = new ArrayList<>();

}
