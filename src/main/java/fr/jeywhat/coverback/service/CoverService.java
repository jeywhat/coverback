package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.helper.ChickenCoopAPIHelper;
import fr.jeywhat.coverback.helper.GameHelper;
import fr.jeywhat.coverback.model.ChikenCoopAPIModel;
import fr.jeywhat.coverback.model.GameInformation;
import fr.jeywhat.coverback.model.Game;
import fr.jeywhat.coverback.repository.GameRepository;
import fr.jeywhat.coverback.repository.model.GameEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CoverService {

    @Value("${storage.location}")
    private String storageLocation;

    private GameSearchService gameSearchService;

    private GameRepository gameRepository;

    public CoverService(GameSearchService gameSearchService, GameRepository gameRepository){
        this.gameSearchService = gameSearchService;
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    private void init(){
        gameSearchService.getGameList().forEach(this::getGameCover);
    }

    private ChikenCoopAPIModel getGameCover(Game game){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = ChickenCoopAPIHelper.requestBuilderURI(game.getName());
        ResponseEntity<ChikenCoopAPIModel> response
                = restTemplate.getForEntity(fooResourceUrl, ChikenCoopAPIModel.class);

        insertCoverIntoBDD(Objects.requireNonNull(response.getBody()).getResult(), game);

        return response.getBody();
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(storageLocation).toAbsolutePath().normalize().resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
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
        GameEntity gameEntity = GameEntity.builder().title(gameInformation.getTitle())
                .releaseDate(gameInformation.getReleaseDate())
                .description(gameInformation.getDescription())
                .genre(String.join(", ", gameInformation.getGenre()))
                .developer(gameInformation.getDeveloper()).score(gameInformation.getScore())
                .rating(gameInformation.getRating())
                .fullpath(game.getFullpath())
                .namefile(game.getName())
                .extension(game.getExtension())
                .size(game.getSize())
                .image(GameHelper.convertURLtoByteArray(gameInformation.getImage()))
                .createOn(new Date())
                .build();
        gameRepository.save(gameEntity);
        System.out.println("Game : "+game.getName()+" Inserted !");
    }
}
