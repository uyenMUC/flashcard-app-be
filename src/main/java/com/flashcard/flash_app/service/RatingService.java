package com.flashcard.flash_app.service;

import com.flashcard.flash_app.dto.request.RatingRequest;
import com.flashcard.flash_app.entity.Rating;
import com.flashcard.flash_app.entity.User;
import com.flashcard.flash_app.exception.AppException;
import com.flashcard.flash_app.exception.ErrorCode;
import com.flashcard.flash_app.repository.DeckRepository;
import com.flashcard.flash_app.repository.RatingRepository;
import com.flashcard.flash_app.repository.UserRepository;
import lombok.AccessLevel;
import com.flashcard.flash_app.entity.Deck;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingService {
    DeckRepository deckRepository;
    UserRepository userRepository;
    RatingRepository ratingRepository;

    public void rateDeck(String user_id, String deck_id, RatingRequest request) {
        Deck deck = deckRepository.getById(deck_id);
        User user = userRepository.getById(user_id);
        if (deck == null || deck.getStatus() == 0)
            throw new AppException(ErrorCode.RATE_DECK_PRIVATE);
        Rating rating = ratingRepository.findByDeckAndUser(deck, user);
        if (rating == null) {
            rating = new Rating();
            rating.setRateValue(request.getRateValue());
            rating.setUser(user);
            rating.setDeck(deck);
        } else {
            rating.setRateValue(request.getRateValue());
        }
        ratingRepository.save(rating);
    }

}
