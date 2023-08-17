package com.noble.developers.controller;

import com.noble.developers.models.DirectorStat;
import com.noble.developers.services.DirectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

     /**
      List Directors Order by Total Number Of Films
     */
    @GetMapping
    public ResponseEntity<List<DirectorStat>> getDirectorsOrderBy(){
        return new ResponseEntity<>(directorService.listDirectorsOrderBy(), HttpStatus.OK);
    }

}
