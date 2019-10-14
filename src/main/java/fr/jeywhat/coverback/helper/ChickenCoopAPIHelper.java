package fr.jeywhat.coverback.helper;

public class ChickenCoopAPIHelper {

    static final public String URL = "https://chicken-coop.fr/rest/games/";
    static final public String PLATFORM = "switch";

    public static String requestBuilderURI(String gameTitle){
        return URL + gameTitle + "?platform=" + PLATFORM;
    }



}
