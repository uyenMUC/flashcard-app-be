package com.flashcard.flash_app.service.impl;

import com.flashcard.flash_app.dto.request.CardCreateRequest;
import com.flashcard.flash_app.dto.request.CardUpdateRequest;
import com.flashcard.flash_app.dto.response.CardResponse;
import com.flashcard.flash_app.entity.Card;
import com.flashcard.flash_app.mapper.CardMapper;
import com.flashcard.flash_app.repository.CardRepository;
import com.flashcard.flash_app.repository.DeckRepository;
import com.flashcard.flash_app.service.CardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardServiceImpl implements CardService {

    CardMapper cardMapper;
    DeckRepository deckRepository;
    CardRepository cardRepository;

    @Override
    public CardResponse createCard(CardCreateRequest request) {
        Card card = cardMapper.toCard(request);
        card.setDeck(deckRepository.getById(request.getDeckId()));
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    public CardResponse getCardById(String cardId) {
        Card card = cardRepository.getById(cardId);
        return cardMapper.toCardResponse(card);
    }

    @Override
    public List<CardResponse> getAllCards(String deckId) {
        List<Card> cards = cardRepository.findCardsByDeck(deckRepository.getById(deckId));
        return cardMapper.toCardResponses(cards);
    }

    @Override
    public CardResponse updateCard(String cardId, CardUpdateRequest request) {
        Card card = cardRepository.getCardById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        cardMapper.updateCard(card, request);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    public void deleteCard(String cardId) {
        cardRepository.deleteById(cardId);
    }
}
