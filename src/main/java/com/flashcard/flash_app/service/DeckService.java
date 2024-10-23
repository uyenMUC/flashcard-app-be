package com.flashcard.flash_app.service;

import com.flashcard.flash_app.dto.request.ApiResponse;
import com.flashcard.flash_app.dto.request.DeckCreateRequest;
import com.flashcard.flash_app.dto.request.DeckUpdateRequest;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.entity.Deck;
import com.flashcard.flash_app.exception.AppException;
import com.flashcard.flash_app.exception.ErrorCode;
import com.flashcard.flash_app.mapper.DeckMapper;
import com.flashcard.flash_app.repository.DeckRepository;
import com.flashcard.flash_app.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeckService {
    private static final Logger log = LoggerFactory.getLogger(DeckService.class);
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

    private boolean isAuthorized(String userId, String deckId) {
        Deck deck = deckRepository.getById(deckId);
        return deck.getUser().getId().equals(userId);
    }

}
