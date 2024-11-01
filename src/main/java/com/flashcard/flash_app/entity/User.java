package com.flashcard.flash_app.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String email;
    String password;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @OneToMany(mappedBy="user")
    Set<Deck> decks;

    @OneToMany(mappedBy="user")
    Set<Rating> ratings;

}
