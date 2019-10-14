package fr.jeywhat.coverback.controller;

import fr.jeywhat.coverback.repository.CoverRepository;
import fr.jeywhat.coverback.repository.model.CoverEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController

public class CoverController {

    @Autowired
    private CoverRepository coverRepository;

    @GetMapping(path = "/game/{name}/info")
    private @ResponseBody Optional<CoverEntity> getCover(@PathVariable String name){
        return coverRepository.findById(name);
    }

    //TODO
    @GetMapping(path = "/game/{name}/download")
    private @ResponseBody Optional<CoverEntity> downloadGame(@PathVariable String name){
        return coverRepository.findById(name);
    }

    @GetMapping(path = "/games")
    private @ResponseBody List<CoverEntity> getAllCovers(){
        return coverRepository.findAll();
    }



}
