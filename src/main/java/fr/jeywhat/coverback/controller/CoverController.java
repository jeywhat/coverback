package fr.jeywhat.coverback.controller;

import fr.jeywhat.coverback.repository.model.GameEntity;
import fr.jeywhat.coverback.service.CoverService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CoverController {

    private CoverService coverService;

    @GetMapping(path = "/game/{name}/info")
    private @ResponseBody Optional<GameEntity> getCover(@PathVariable String name){
        return coverService.findGameByID(name);
    }

    //TODO
    @GetMapping(path = "/game/{name}/download")
    private @ResponseBody Optional<GameEntity> downloadGame(@PathVariable String name){
        return null;
    }

    @GetMapping(path = "/games")
    private @ResponseBody List<GameEntity> getAllCovers(){
        return coverService.findAllGames();
    }



}
