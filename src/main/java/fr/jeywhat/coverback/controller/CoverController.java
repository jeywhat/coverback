package fr.jeywhat.coverback.controller;

import fr.jeywhat.coverback.repository.model.GameEntity;
import fr.jeywhat.coverback.service.CoverService;
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

}
