package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.helper.ChickenCoopAPIHelper;
import fr.jeywhat.coverback.helper.GameHelper;
import fr.jeywhat.coverback.model.ChikenCoopAPIModel;
import fr.jeywhat.coverback.model.GameInformation;
import fr.jeywhat.coverback.model.Game;
import fr.jeywhat.coverback.repository.GameRepository;
import fr.jeywhat.coverback.repository.model.GameEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CoverService {

    private static final Logger logger = LoggerFactory.getLogger(CoverService.class);

    @Value("${storage.location}")
    private String storageLocation;

    private GameRepository gameRepository;

    public CoverService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    public boolean addGame(Game game){
        RestTemplate restTemplate = new RestTemplate();
        GameInformation gameInformation = new GameInformation();

        try{
            String fooResourceUrl
                    = ChickenCoopAPIHelper.requestBuilderURI(game.getName());
            ResponseEntity<ChikenCoopAPIModel> response = restTemplate.getForEntity(fooResourceUrl, ChikenCoopAPIModel.class);
            gameInformation = Objects.requireNonNull(response.getBody()).getResult();
        }catch(Exception e){
            logger.error("Can not retrieve game information : {}", game.getName());
        }

        insertCoverIntoBDD(gameInformation, game);

        return true;
    }

    public boolean removeGame(String gameTitle){
        gameRepository.deleteById(gameTitle);
        return true;
    }

    public Resource loadFileAsResource(String pathname) {
        try {
            Path path = Paths.get(pathname);
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
        }
        return null;
    }

    public Optional<GameEntity> findGameByID(String name){
        return gameRepository.findById(name);
    }

    public List<GameEntity> findAllGames(){
        return gameRepository.findAll();
    }

    @Transactional
    public void insertCoverIntoBDD(GameInformation gameInformation, Game game){
        GameEntity gameEntity = GameEntity.builder()
                .namefile(game.getName())
                .fullpath(game.getFullpath())
                .extension(game.getExtension())
                .size(game.getSize())
                .title(gameInformation.getTitle())
                .releaseDate(gameInformation.getReleaseDate())
                .description(gameInformation.getDescription())
                .genre(String.join(", ", gameInformation.getGenre()))
                .developer(gameInformation.getDeveloper()).score(gameInformation.getScore())
                .rating(gameInformation.getRating())
                .image(GameHelper.convertURLtoByteArray(gameInformation.getImage()))
                .canBeDownloaded(true)
                .createOn(new Date())
                .build();
        gameRepository.save(gameEntity);
        logger.info("Inserted : Game : "+game.getName());
    }
}
