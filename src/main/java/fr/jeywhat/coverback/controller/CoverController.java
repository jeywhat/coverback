package fr.jeywhat.coverback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.jeywhat.coverback.model.RefreshGames;
import fr.jeywhat.coverback.repository.model.GameEntity;
import fr.jeywhat.coverback.service.CoverService;
import fr.jeywhat.coverback.service.GameSearchService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("game")
@CrossOrigin
@AllArgsConstructor
public class CoverController {

    private CoverService coverService;

    private GameSearchService gameSearchService;

    @GetMapping(path = "/{name}", produces = "application/json")
    private @ResponseBody Optional<GameEntity> getGame(@PathVariable String name){
        return coverService.findGameByID(name);
    }

    @GetMapping(path = "/{name}/download")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String name) {
        return coverService.downloadGame(name);
    }

    @GetMapping(path = "/all")
    private @ResponseBody List<GameEntity> getAllCovers(){
        return coverService.findAllGames();
    }

    @GetMapping(path = "/findNewGames")
    private @ResponseBody String getNewGamesAdded(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String arrayToJson = "No game found !";
        try {
            List<GameEntity> newGames = gameSearchService.searchGames();
            if(!newGames.isEmpty()){
                arrayToJson = newGames.size()+" game(s) found : \n";
                arrayToJson += objectMapper.writeValueAsString(newGames);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return arrayToJson;
    }

    @PostMapping("/refresh")
    public @ResponseBody boolean postResponseController(
            @RequestBody RefreshGames refreshGames) {

        if(refreshGames.isRefreshAllGames()){
            coverService.refreshGame(false);
        }else{
            coverService.refreshGame(true);
        }

        return true;
    }



}
