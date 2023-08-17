package com.noble.developers.services;

import com.noble.developers.dto.FilmDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FilmService {
    List<FilmDto> getAll();
    List<FilmDto> getTopFiveFilms();
    List<FilmDto> findAllFilmsByNameContaining(String filmName);
    void rateFilm(int filmID,int userID,int rate);
    List<FilmDto> getRecommendedFilms(int userID);
}
