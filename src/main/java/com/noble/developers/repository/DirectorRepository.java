package com.noble.developers.repository;

import com.noble.developers.models.DirectorStat;
import com.noble.developers.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director,Integer> {
    @Query(nativeQuery = true)
    List<DirectorStat> getDirectorsOrderByFilms();

}
