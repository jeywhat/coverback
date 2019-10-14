package fr.jeywhat.coverback.controller;

import fr.jeywhat.coverback.repository.model.GameEntity;
import fr.jeywhat.coverback.service.CoverService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("game")
@AllArgsConstructor
public class CoverController {

    private static final Logger logger = LoggerFactory.getLogger(CoverController.class);

    private CoverService coverService;

    @GetMapping(path = "/{name}")
    private @ResponseBody Optional<GameEntity> getCover(@PathVariable String name){
        return coverService.findGameByID(name);
    }

    //TODO
    @GetMapping(path = "/{name}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable String name, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = coverService.loadFileAsResource(name);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(path = "/game/all")
    private @ResponseBody List<GameEntity> getAllCovers(){
        return coverService.findAllGames();
    }

}
