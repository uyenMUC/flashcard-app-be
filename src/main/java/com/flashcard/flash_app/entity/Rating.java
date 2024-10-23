package com.flashcard.flash_app.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    int rateValue;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    User user;

    @ManyToOne
    @JoinColumn(name="deck_id", nullable=false)
    Deck deck;
}
