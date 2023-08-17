package com.noble.developers.utils;

import com.noble.developers.dto.FilmDto;
import com.noble.developers.models.Film;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 Utility Class for Mapping Entity to DTO
 */
@Component
public class Mapper {

    public static FilmDto mapFilmFromEntity(Film model){
        FilmDto filmDto = new FilmDto();
        filmDto.setId(model.getId());
        filmDto.setName(model.getName());
        filmDto.setGenere(model.getGenere());
        filmDto.setDirectorName(model.getDirector().getName());
        Double roundedAverageRate = BigDecimal.valueOf(model.getAverageRate())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        filmDto.setAverageRate(roundedAverageRate);
        return filmDto;
    }
}
