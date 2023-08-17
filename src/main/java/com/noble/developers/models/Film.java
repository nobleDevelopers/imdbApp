package com.noble.developers.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(
            name = "director_id"
    )
    private Director director;
    @Enumerated(EnumType.ORDINAL)
    private Genere genere;
    @ColumnDefault("0")
    private double averageRate;
    @OneToMany(mappedBy = "film")
    private List<Rank> ranks;
}
