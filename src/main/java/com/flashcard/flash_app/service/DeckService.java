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

    public DeckResponse createDeck(String user_id, DeckCreateRequest request) {
        Deck deck = deckMapper.toDeck(request);
        deck.setUser(userRepository.getById(user_id));
        return deckMapper.toDeckResponse(deckRepository.save(deck));
    }

    public DeckResponse getDeckById(String user_id, String deck_id) {
        Deck deck = deckRepository.getDeckById(deck_id)
                .orElseThrow(() -> new RuntimeException("Deck not found"));

        if (deck.getStatus() == 0 && !isAuthorized(user_id, deck_id)) {
            throw new RuntimeException("Not authorized");
        }
        return deckMapper.toDeckResponse(deck);
    }

    public List<DeckResponse> getAllDecks(String user_id) {
        List<Deck> decks = deckRepository.findDecksByUser(userRepository.getById(user_id));
        return deckMapper.toDeckResponses(decks);
    }

    public DeckResponse updateDeck(String user_id, String deck_id, DeckUpdateRequest request) {
        if (!isAuthorized(user_id, deck_id)) {
            throw new RuntimeException("Not authorized");
        }
        Deck deck = deckRepository.getDeckById(deck_id)
                .orElseThrow(() -> new RuntimeException("Deck not found"));
        deckMapper.updateDeck(deck, request);
        return deckMapper.toDeckResponse(deckRepository.save(deck));
    }

    public void deleteDeck(String user_id, String deck_id) {
        if (!isAuthorized(user_id, deck_id)) {
            throw new RuntimeException("Not authorized");
        }
        deckRepository.deleteById(deck_id);
    }

    private boolean isAuthorized(String user_id, String deck_id) {
        Deck deck = deckRepository.getById(deck_id);
        return deck.getUser().getId().equals(user_id);
    }

}
