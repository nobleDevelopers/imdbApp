package com.noble.developers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;

@SqlResultSetMapping(
        name = "UserStatMapping",
        classes = @ConstructorResult(
                targetClass = UserStat.class,
                columns = {
                        @ColumnResult(name = "userName", type = String.class),
                        @ColumnResult(name = "numberOfWatchedFilms", type = Integer.class)
                }
        )
)
@NamedNativeQueries({
        @NamedNativeQuery(name = "Rank.getUserByMaxWatchedFilm",query =
                "SELECT TOP 1 Users.user_name as userName, count(*) as numberOfWatchedFilms FROM Users " +
                        "INNER JOIN Rank ON Users.id = Rank.user_id GROUP BY Users.user_name ORDER BY count(*) DESC",
                resultSetMapping = "UserStatMapping")
})

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStat {
    private String name;
    private int numberOfWatchedFilms;
}
