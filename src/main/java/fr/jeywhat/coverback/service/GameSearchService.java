package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.helper.GameHelper;
import fr.jeywhat.coverback.model.Game;
import fr.jeywhat.coverback.repository.model.GameEntity;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Getter
public class GameSearchService {

    @Value("${storage.location}")
    private String storageLocation;

    @Value("#{'${supported.extensions.files}'.split(',')}")
    private List<String> supportedExtensionFiles;

    @Value("#{'${ignored.prefix.files}'.split(',')}")
    private List<String> ignoredPrefixFiles;

    @Value("${init.scan.games.enabled:true}")
    private boolean enabledInitScanGames;

    private CoverService coverService;

    private static final Logger logger = LoggerFactory.getLogger(GameSearchService.class);

    public GameSearchService(CoverService coverService){
        this.coverService = coverService;
    }

    @PostConstruct
    private void init(){
        if(enabledInitScanGames){
            new Thread(this::searchGames).start();
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public List<GameEntity> searchGames() {
        logger.info("Beginning Games Scan !");
        File currentDir = new File(this.storageLocation);
        List<GameEntity> newGames = displayDirectoryContents(currentDir);
        logger.info("Added {} New Game(s) !", newGames.size());
        logger.info("Finishing Games Scan !");
        return newGames;
    }

    private List<GameEntity> displayDirectoryContents(File dir) {
        List<GameEntity> newGamesAdded = new ArrayList<>();
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                newGamesAdded.addAll(displayDirectoryContents(file));
            } else {
                if(GameHelper.isSupportedFile(file, supportedExtensionFiles, ignoredPrefixFiles)){
                    Game tmpGame = new Game(file);
                    Optional<GameEntity> entity = coverService.findGameByID(tmpGame.getName());
                    if(entity.isEmpty()){
                        newGamesAdded.add(coverService.addGame(tmpGame));
                    }
                }
            }
        }
        return newGamesAdded;
    }
}
