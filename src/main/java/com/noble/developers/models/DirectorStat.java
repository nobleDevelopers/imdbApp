package com.noble.developers.models;

import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "DirectorStatMapping",
        classes = @ConstructorResult(
                targetClass = DirectorStat.class,
                columns = {
                        @ColumnResult(name = "directorName", type = String.class),
                        @ColumnResult(name = "totalNumberOfFilms", type = Integer.class)
                }
        )
)
@NamedNativeQueries({
        @NamedNativeQuery(name = "Director.getDirectorsOrderByFilms",query =
                "SELECT Director.name as directorName, count(*) as totalNumberOfFilms FROM Director " +
                        "INNER JOIN Film ON Director.id = Film.director_id GROUP BY Director.name ORDER BY count(*) DESC",
                resultSetMapping = "DirectorStatMapping")
})
public class DirectorStat {
    private String directorName;
    private int totalNumberOfFilms;

    public DirectorStat(String name, int totalNumberOfFilms) {
        this.directorName = name;
        this.totalNumberOfFilms = totalNumberOfFilms;
    }

}
