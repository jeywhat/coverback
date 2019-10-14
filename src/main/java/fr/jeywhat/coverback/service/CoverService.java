package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.helper.ChickenCoopAPIHelper;
import fr.jeywhat.coverback.helper.MethodHelper;
import fr.jeywhat.coverback.model.ChikenCoopAPIModel;
import fr.jeywhat.coverback.model.GameInformation;
import fr.jeywhat.coverback.repository.GameRepository;
import fr.jeywhat.coverback.repository.model.GameEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CoverService {

    private GameRepository gameRepository;

    public CoverService (GameRepository gameRepository){
        this.gameRepository = gameRepository;
        this.getGameCover("Super Smash Bros");
        this.getGameCover("Undertale");
        this.getGameCover("Super Mario Maker 2");
        this.getGameCover("Mario Kart 8 Deluxe");
    }

    private ChikenCoopAPIModel getGameCover(String gameTitle){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = ChickenCoopAPIHelper.requestBuilderURI(gameTitle);
        ResponseEntity<ChikenCoopAPIModel> response
                = restTemplate.getForEntity(fooResourceUrl, ChikenCoopAPIModel.class);

        insertCoverIntoBDD(Objects.requireNonNull(response.getBody()).getResult());

        return response.getBody();
    }

    public Optional<GameEntity> findGameByID(String name){
        return gameRepository.findById(name);
    }

    public List<GameEntity> findAllGames(){
        return gameRepository.findAll();
    }

    @Transactional
    public void insertCoverIntoBDD(GameInformation gameInformation){
        GameEntity gameEntity = GameEntity.builder().title(gameInformation.getTitle())
                .releaseDate(gameInformation.getReleaseDate())
                .description(gameInformation.getDescription())
                .genre(String.join(", ", gameInformation.getGenre()))
                .developer(gameInformation.getDeveloper()).score(gameInformation.getScore())
                .rating(gameInformation.getRating())
                .image(MethodHelper.convertURLtoByteArray(gameInformation.getImage()))
                .createOn(new Date())
                .build();
        gameRepository.save(gameEntity);
    }
}
