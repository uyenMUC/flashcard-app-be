package com.flashcard.flash_app.service;

import com.flashcard.flash_app.dto.request.RatingRequest;
import com.flashcard.flash_app.dto.response.DeckResponse;

public interface RatingService {
    public DeckResponse rateDeck(String userId, String deckId, RatingRequest request);
}
