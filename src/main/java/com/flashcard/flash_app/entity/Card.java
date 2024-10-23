package com.flashcard.flash_app.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String frontText;
    String backText;
    String image = null;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="deck_id", nullable=false)
    Deck deck;
}
