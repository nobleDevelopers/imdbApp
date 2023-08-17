package com.noble.developers.repository;

import com.hacker.imdbapp.models.*;
import com.noble.developers.models.Film;
import com.noble.developers.models.Rank;
import com.noble.developers.models.User;
import com.noble.developers.models.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RankRepository extends JpaRepository<Rank,Integer> {

    Rank findByUserAndFilm(User user, Film film);

    @Query("SELECT AVG(R.rate) FROM Rank R WHERE R.film.id=:id GROUP BY R.film.id")
    double calculateAverageRate(@Param("id") int filmID);

    List<Rank> findAllByUser_Id(int userID);

    @Query(nativeQuery = true)
    UserStat getUserByMaxWatchedFilm();

}
