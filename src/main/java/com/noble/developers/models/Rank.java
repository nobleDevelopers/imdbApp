package com.noble.developers.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
    This model is implemented to track History of Users Rating on Films
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;
    @ManyToOne
    @JoinColumn(
            name = "film_id"
    )
    private Film film;

    private int rate;
}
