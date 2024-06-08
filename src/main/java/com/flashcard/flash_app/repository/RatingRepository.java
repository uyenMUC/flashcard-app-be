package com.flashcard.flash_app.repository;

import com.flashcard.flash_app.entity.Deck;
import com.flashcard.flash_app.entity.Rating;
import com.flashcard.flash_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
    Rating findByDeckAndUser(Deck deck, User user);
}
