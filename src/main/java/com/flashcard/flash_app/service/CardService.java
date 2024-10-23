package com.flashcard.flash_app.service;

import com.flashcard.flash_app.dto.request.CardCreateRequest;
import com.flashcard.flash_app.dto.request.CardUpdateRequest;
import com.flashcard.flash_app.dto.response.CardResponse;
import com.flashcard.flash_app.entity.Card;
import com.flashcard.flash_app.mapper.CardMapper;
import com.flashcard.flash_app.repository.CardRepository;
import com.flashcard.flash_app.repository.DeckRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardService {

    CardMapper cardMapper;
    DeckRepository deckRepository;
    CardRepository cardRepository;

    public CardResponse createCard(CardCreateRequest request) {
        Card card = cardMapper.toCard(request);
        card.setDeck(deckRepository.getById(request.getDeckId()));
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    public CardResponse getCardById(String cardId) {
        Card card = cardRepository.getById(cardId);
        return cardMapper.toCardResponse(card);
    }

    public List<CardResponse> getAllCards(String deckId) {
        List<Card> cards = cardRepository.findCardsByDeck(deckRepository.getById(deckId));
        return cardMapper.toCardResponses(cards);
    }

    public CardResponse updateCard(String cardId, CardUpdateRequest request) {
        Card card = cardRepository.getCardById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        cardMapper.updateCard(card, request);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    public void deleteCard(String cardId) {
        cardRepository.deleteById(cardId);
    }
}
