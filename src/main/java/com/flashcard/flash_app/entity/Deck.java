package com.flashcard.flash_app.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String description;
    float rate = 0.0F;
    Long numOfRatings = 0L;
    int status = 0; // 0: private, 1: public
    LocalDateTime created_at;
    LocalDateTime updated_at;


    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    User user;

    @OneToMany(mappedBy="deck")
    Set<Rating> ratings;

    @OneToMany(mappedBy="deck")
    Set<Card> cards;
}
