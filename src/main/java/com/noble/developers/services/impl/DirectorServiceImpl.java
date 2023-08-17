package com.noble.developers.services.impl;

import com.noble.developers.exceptions.DirectorNotFoundException;
import com.noble.developers.models.DirectorStat;
import com.noble.developers.repository.DirectorRepository;
import com.noble.developers.services.DirectorService;

import java.util.List;

public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public List<DirectorStat> listDirectorsOrderBy() {
        List<DirectorStat> directorsOrderByFilms = directorRepository.getDirectorsOrderByFilms();
        if(directorsOrderByFilms == null || directorsOrderByFilms.isEmpty())
            throw new DirectorNotFoundException("No Director Existed");
        return directorsOrderByFilms;
    }
}
