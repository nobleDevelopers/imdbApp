package com.noble.developers.services.impl;

import com.noble.developers.dto.FilmDto;
import com.noble.developers.models.Film;
import com.noble.developers.models.Genere;
import com.noble.developers.models.Rank;
import com.noble.developers.models.User;
import com.noble.developers.repository.FilmRepository;
import com.noble.developers.repository.RankRepository;
import com.noble.developers.repository.UserRepository;
import com.noble.developers.services.FilmService;
import com.noble.developers.utils.Mapper;
import com.noble.developers.exceptions.*;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final UserRepository userRepository;

    private final RankRepository rankRepository;

    public FilmServiceImpl(FilmRepository filmRepository, UserRepository userRepository, RankRepository rankRepository) {
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.rankRepository = rankRepository;
    }

    /**
     * This method retrieves all films from the repository, transforms them into DTOs (Data Transfer Objects),
     * and returns them as a list. If no films exist in the database, a FilmNotFoundException is thrown.
     *
     * @return a list of FilmDto objects representing all films in the database
     * @throws FilmNotFoundException when no films exist in the database
     */

    @Override
    public List<FilmDto> getAll() {
        try {
            List<FilmDto> list =
                    filmRepository.findAll().stream()
                            .map(Mapper::mapFilmFromEntity)
                            .collect(Collectors.toList());
            if (list.isEmpty())
                throw new FilmNotFoundException("No Film is existed in database");
            return list;
        }
        catch (NullPointerException e) {
            throw new FilmNotFoundException("Null object encountered");
        }
        catch (Exception e) {
            throw new FilmProcessingException("Bad Format in Film Data or Unknown Processing data Exception");
        }
    }

    /**
     * This method retrieves Top 5 films from the repository with higher Average Rate ,
     * If no films exist in the database, a FilmNotFoundException is thrown.
     *
     * @return a list of FilmDto objects representing Top 5 films in the database
     * @throws FilmNotFoundException when no films exist in the database
     */
    @Override
    public List<FilmDto> getTopFiveFilms() {
        try {
            List<FilmDto> list =
                    filmRepository.findTop5ByOrderByAverageRateDesc().stream()
                            .map(Mapper::mapFilmFromEntity)
                            .collect(Collectors.toList());
            if (list.isEmpty())
                throw new FilmNotFoundException("No Film is existed in database");
            return list;
        }
        catch (NullPointerException e) {
            throw new FilmNotFoundException("Null object encountered");
        }
        catch (Exception e) {
            throw new FilmProcessingException("Bad Format in Film Data or Unknown Processing data Exception");
        }
    }

    /**
     * This method retrieves Films that contain input names ,
     * If no films exist in the database, a FilmNotFoundException is thrown.
     *
     * @return a list of FilmDto objects representing Top 5 films in the database
     * @throws FilmNotFoundException when no films exist in the database
     */

    @Override
    public List<FilmDto> findAllFilmsByNameContaining(String name) {
        try {
            List<FilmDto> list =
                    filmRepository.findAllFilmsByNameContaining(name).stream()
                            .map(film -> Mapper.mapFilmFromEntity(film)).collect(Collectors.toList());
            if (list.isEmpty())
                throw new FilmNotFoundException("No Film is existed with given name");
            return list;
        }
        catch (NullPointerException e) {
            throw new FilmNotFoundException("Null object encountered");
        }
        catch (Exception e) {
            throw new FilmProcessingException("Bad Format in Film Data or Unknown Processing data Exception");
        }
    }
    /**
    * This method is used to rate a film by input user
    * Each Rating must be persisted in a Rank table to track history
    * Average Rate of the film will be updated after each rating
    * If Rate is not in range of 1 - 5 , and InvalidRateException will be raised
    * If User has already rated input film, ALREADY_REPORTED HTTP Status code will be displayed
    */
    @Override
    public void rateFilm(int filmID, int userID, int rate) {
        if(rate<1 || rate>5)
            throw new InvalidRateException("Rate must be between 1 and 5");
        Film film =
                filmRepository.findById(filmID).orElseThrow(()->new FilmNotFoundException("Film with the id is not existed"));
        User user =
                userRepository.findById(userID).orElseThrow(()->new UserNotFoundException("User with the id is not existed"));
        Rank byUserAndFilm = rankRepository.findByUserAndFilm(user, film);
        if(byUserAndFilm != null)
            throw new DuplicateRankException("This film is already ranked by this user");

        Rank newRank = new Rank();
        newRank.setFilm(film);
        newRank.setUser(user);
        newRank.setRate(rate);
        rankRepository.save(newRank);

        double average = rankRepository.calculateAverageRate(filmID);
        film.setAverageRate(average);
        filmRepository.save(film);

    }

    /**
        This method returns Recommended Films by this sequence:
        * As a General Rule: A film that is rated/watched by user will not be recommended
        * If user has not been rated any film, all films will be recommended
        * First Candidates containing All Films with Same Director and Same Genre with Higher Average Rate
        * Second Candidates containing All Films with Same Director or Same Genre with Higher Average Rate
        * Return Union of both FirstCandidates and SecondCandidates
        * In case no film is existed in both list, those which users has not been rated yet, will be recommended
     */

    @Override
    public List<FilmDto> getRecommendedFilms(int userID) {
        // Check if user exists, throw exception if not
        userRepository.findById(userID).orElseThrow(()->new UserNotFoundException("User with the id is not existed"));

        // Retrieve user's ranking history
        List<Rank> rankList =
                rankRepository.findAllByUser_Id(userID);

        // If user has no ranking history, recommend top-rated films
        if(rankList.isEmpty())
            return filmRepository.findAll(Sort.by(Sort.Direction.DESC,"averageRate")).stream()
                    .map(Mapper::mapFilmFromEntity)
                    .collect(Collectors.toList());

        // Extract distinct genres, directors, and watched film IDs from user's ranking history
        HashSet<Genere> distinctGenres = new HashSet<>();
        HashSet<Integer> distinctDirectorIDs = new HashSet<>();
        HashSet<Integer> distinctWatchedFilmIDs = new HashSet<>();
        for (Rank rank:
                rankList) {
            distinctWatchedFilmIDs.add(rank.getFilm().getId());
            distinctGenres.add(rank.getFilm().getGenere());
            distinctDirectorIDs.add(rank.getFilm().getDirector().getId());
        }
        // First set of recommendations based on exact matches (genre & director)
        List<FilmDto> firstCandidates =  filmRepository.
                findAllByGenereInAndDirector_IdInAndIdNotInOrderByAverageRateDesc(distinctGenres.stream().toList(),
                        distinctDirectorIDs.stream().toList(),distinctWatchedFilmIDs.stream().toList())
                .stream()
                .map(Mapper::mapFilmFromEntity)
                .collect(Collectors.toList());

        // Mark first set of recommended films as watched
        firstCandidates.forEach(film -> distinctWatchedFilmIDs.add(film.getId()));

        // Second set of recommendations based on genre or director
        List<FilmDto> secondCandidates =  filmRepository.
                findAllByGenereInOrDirector_IdInOrderByAverageRateDesc(distinctGenres.stream().toList(),
                        distinctDirectorIDs.stream().toList())
                .stream()
                .filter(film -> !distinctWatchedFilmIDs.contains(film.getId()))
                .map(film -> Mapper.mapFilmFromEntity(film))
                .collect(Collectors.toList());

        // Combine first and second set of candidates
        List<FilmDto> results = new ArrayList<>();
        results.addAll(firstCandidates);
        results.addAll(secondCandidates);

        // If no recommendations found, return top-rated unwatched films
        if(results.isEmpty())
            return filmRepository.findAllByIdNotInOrderByAverageRateDesc(distinctWatchedFilmIDs.stream().toList())
                    .stream()
                    .map(film -> Mapper.mapFilmFromEntity(film))
                    .collect(Collectors.toList());
        // Return the combined recommendations
        return results;

    }
}

