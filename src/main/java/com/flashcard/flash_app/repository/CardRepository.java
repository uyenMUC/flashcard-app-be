package com.flashcard.flash_app.repository;

import com.flashcard.flash_app.entity.Card;
import com.flashcard.flash_app.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    Optional<Card> getCardById(String id);
    List<Card> findCardsByDeck(Deck deck);
}
