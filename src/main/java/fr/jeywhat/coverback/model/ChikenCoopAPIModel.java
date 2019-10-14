package fr.jeywhat.coverback.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChikenCoopAPIModel {

    private String query;
    private double executionTime;
    private GameInformation result = new GameInformation();

}
