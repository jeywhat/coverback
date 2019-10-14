package fr.jeywhat.coverback.service;

import fr.jeywhat.coverback.helper.ChickenCoopAPIHelper;
import fr.jeywhat.coverback.helper.MethodHelper;
import fr.jeywhat.coverback.model.ChikenCoopAPIModel;
import fr.jeywhat.coverback.model.GameInformation;
import fr.jeywhat.coverback.repository.CoverRepository;
import fr.jeywhat.coverback.repository.model.CoverEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;

@Service
public class CoverService {

    private CoverRepository coverRepository;

    public CoverService (CoverRepository coverRepository){
        this.coverRepository = coverRepository;
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

    @Transactional
    public void insertCoverIntoBDD(GameInformation gameInformation){
        CoverEntity coverEntity = CoverEntity.builder().title(gameInformation.getTitle())
                .releaseDate(gameInformation.getReleaseDate())
                .description(gameInformation.getDescription())
                .genre(String.join(", ", gameInformation.getGenre()))
                .developer(gameInformation.getDeveloper()).score(gameInformation.getScore())
                .rating(gameInformation.getRating())
                .image(MethodHelper.convertURLtoByteArray(gameInformation.getImage()))
                .createOn(new Date())
                .build();
        coverRepository.save(coverEntity);
        System.out.println("SUCCESS INSERTED : " + gameInformation.getTitle());
    }
}
