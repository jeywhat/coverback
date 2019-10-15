package fr.jeywhat.coverback.controller;

import fr.jeywhat.coverback.repository.model.GameEntity;
import fr.jeywhat.coverback.service.CoverService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("game")
@AllArgsConstructor
public class CoverController {

    private static final Logger logger = LoggerFactory.getLogger(CoverController.class);

    private CoverService coverService;

    @GetMapping(path = "/{name}", produces = "application/json")
    private @ResponseBody Optional<GameEntity> getGame(@PathVariable String name){
        return coverService.findGameByID(name);
    }

    @GetMapping(path = "/{name}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable String name) {
        Optional<GameEntity> game = coverService.findGameByID(name);
        if(game.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Resource resource = coverService.loadFileAsResource(game.get().getFullpath());

        if(resource == null){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(path = "/all")
    private @ResponseBody List<GameEntity> getAllCovers(){
        return coverService.findAllGames();
    }

}
