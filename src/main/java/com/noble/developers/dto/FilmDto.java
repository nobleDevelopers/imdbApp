package com.noble.developers.dto;


import com.noble.developers.models.Genere;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {
    private int id;
    private String name;
    private String directorName;
    private Genere genere;
    private double averageRate;
}
