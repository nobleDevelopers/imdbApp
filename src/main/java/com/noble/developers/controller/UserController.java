package com.noble.developers.controller;

import com.noble.developers.models.UserStat;
import com.noble.developers.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
      List Directors Order by Total Number Of Films
     */

    @GetMapping("/maxWatchedFilms")
    public ResponseEntity<UserStat> getUserByMaxWatchedFilm(){
        return new ResponseEntity<>(userService.getUserByMaxWatchedFilm(), HttpStatus.OK);
    }

}
