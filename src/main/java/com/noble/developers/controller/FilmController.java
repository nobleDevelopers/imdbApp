package com.noble.developers.controller;

import com.noble.developers.dto.FilmDto;
import com.noble.developers.services.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/film")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
         Find All films
    */
    @GetMapping("")
    public ResponseEntity<List<FilmDto>> getAll(){
        return ResponseEntity.ok(filmService.getAll());
    }

    /**
     Find Top 5 Films
    */
    @GetMapping("/top")
    public ResponseEntity<List<FilmDto>> getTopFiveFilms(){
        return ResponseEntity.ok(filmService.getTopFiveFilms());
    }

    /**
      Find All films that includes input name
     */
    @GetMapping("/{filmName}")
    public ResponseEntity<List<FilmDto>> getAllContainingFilmName(@PathVariable("filmName") String filmName){
        return ResponseEntity.ok(filmService.findAllFilmsByNameContaining(filmName));
    }

    /**
        Endpoint to rate a film by a user
    */
    @PutMapping("/{filmID}/rate/{rate}/user/{userID}")
    public ResponseEntity<String> rateFilm(@PathVariable("filmID") int filmID,
                                           @PathVariable("userID") int userID,
                                           @PathVariable("rate") int rate)
    {
        filmService.rateFilm(filmID,userID,rate);
        return ResponseEntity.ok("Film is successfully rated");
    }

    /**
     Endpoint to recommend films to a user
     */
    @GetMapping("/recommend/{userID}")
    public ResponseEntity<List<FilmDto>> getRecommendedFilms(@PathVariable("userID")int userID){
        return new ResponseEntity<>(filmService.getRecommendedFilms(userID), HttpStatus.OK);
    }

}
