package com.flashcard.flash_app.service.impl;

import com.flashcard.flash_app.dto.request.DeckCreateRequest;
import com.flashcard.flash_app.dto.request.DeckUpdateRequest;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.entity.Deck;
import com.flashcard.flash_app.mapper.DeckMapper;
import com.flashcard.flash_app.repository.DeckRepository;
import com.flashcard.flash_app.repository.UserRepository;
import com.flashcard.flash_app.service.DeckService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeckServiceImpl implements DeckService {
    DeckRepository deckRepository;
    UserRepository userRepository;
    DeckMapper deckMapper;

    public DeckResponse createDeck(String userId, DeckCreateRequest request) {
        Deck deck = deckMapper.toDeck(request);
        deck.setUser(userRepository.getById(userId));
        return deckMapper.toDeckResponse(deckRepository.save(deck));
    }

    public DeckResponse getDeckById(String userId, String deckId) {
        Deck deck = deckRepository.getDeckById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck not found"));

        if (deck.getStatus() == 0 && !isAuthorized(userId, deckId)) {
            throw new RuntimeException("Not authorized");
        }
        return deckMapper.toDeckResponse(deck);
    }

    public List<DeckResponse> getAllDecks(String userId) {
        List<Deck> decks = deckRepository.findDecksByUser(userRepository.getById(userId));
        return deckMapper.toDeckResponses(decks);
    }

    public DeckResponse updateDeck(String userId, String deckId, DeckUpdateRequest request) {
        if (!isAuthorized(userId, deckId)) {
            throw new RuntimeException("Not authorized");
        }
        Deck deck = deckRepository.getDeckById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck not found"));
        deckMapper.updateDeck(deck, request);
        return deckMapper.toDeckResponse(deckRepository.save(deck));
    }

    public void deleteDeck(String userId, String deckId) {
        if (!isAuthorized(userId, deckId)) {
            throw new RuntimeException("Not authorized");
        }
        deckRepository.deleteById(deckId);
    }

    public boolean isAuthorized(String userId, String deckId) {
        Deck deck = deckRepository.getDeckById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck not found"));
        return deck.getUser().getId().equals(userId);
    }

}
