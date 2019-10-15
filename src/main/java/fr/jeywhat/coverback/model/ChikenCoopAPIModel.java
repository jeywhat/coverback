package fr.jeywhat.coverback.model;

import lombok.Data;

@Data
public class ChikenCoopAPIModel {

    private String query;

    private double executionTime;

    private GameInformation result = new GameInformation();

}
