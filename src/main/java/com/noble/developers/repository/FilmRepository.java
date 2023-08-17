package com.noble.developers.repository;

import com.noble.developers.models.Film;
import com.noble.developers.models.Genere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film,Integer> {
    List<Film> findAllFilmsByNameContaining(String name);
    List<Film> findTop5ByOrderByAverageRateDesc();
    List<Film> findAllByIdNotInOrderByAverageRateDesc(List<Integer> filmIDList);
    List<Film> findAllByGenereInAndDirector_IdInAndIdNotInOrderByAverageRateDesc(List<Genere> genereList,
                                                           List<Integer> directorIDList,List<Integer> filmIDList);
    List<Film> findAllByGenereInOrDirector_IdInOrderByAverageRateDesc(List<Genere> genereList,
                                                           List<Integer> directorIDList);
}
