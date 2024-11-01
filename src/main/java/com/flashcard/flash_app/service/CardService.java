package com.flashcard.flash_app.service;

import com.flashcard.flash_app.dto.request.CardCreateRequest;
import com.flashcard.flash_app.dto.request.CardUpdateRequest;
import com.flashcard.flash_app.dto.response.CardResponse;

import java.util.List;

public interface CardService {
    public CardResponse createCard(CardCreateRequest request);
    public CardResponse getCardById(String cardId);
    public List<CardResponse> getAllCards(String deckId);
    public CardResponse updateCard(String cardId, CardUpdateRequest request);
    public void deleteCard(String cardId);
}
