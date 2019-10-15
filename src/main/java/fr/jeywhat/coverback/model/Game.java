package fr.jeywhat.coverback.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Game {

    private String fullpath;

    private String name;

    private String extension;

    private BigDecimal size;

}
