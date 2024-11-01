package com.flashcard.flash_app.service;

import com.flashcard.flash_app.dto.request.DeckCreateRequest;
import com.flashcard.flash_app.dto.request.DeckUpdateRequest;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.entity.Deck;

import java.util.List;

public interface DeckService {
    public DeckResponse createDeck(String userId, DeckCreateRequest request) ;
    public DeckResponse getDeckById(String userId, String deckId);
    public List<DeckResponse> getAllDecks(String userId);
    public DeckResponse updateDeck(String userId, String deckId, DeckUpdateRequest request);
    public void deleteDeck(String userId, String deckId);
    public boolean isAuthorized(String userId, String deckId);
}
