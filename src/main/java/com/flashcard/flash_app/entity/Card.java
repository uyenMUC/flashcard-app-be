package com.flashcard.flash_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String front_text;
    String back_text;
    String image = null;
    LocalDateTime created_at;
    LocalDateTime updated_at;
}
