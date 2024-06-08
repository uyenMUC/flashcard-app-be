package com.flashcard.flash_app.repository;

import com.flashcard.flash_app.entity.Deck;
import com.flashcard.flash_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeckRepository extends JpaRepository<Deck, String> {
    Optional<Deck> getDeckById(String id);
    List<Deck> findDecksByUser(User user);
}
