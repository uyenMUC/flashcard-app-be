package com.flashcard.flash_app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.flashcard.flash_app.dto.request.DeckCreateRequest;
import com.flashcard.flash_app.dto.request.DeckUpdateRequest;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.entity.Deck;
import com.flashcard.flash_app.entity.User;
import com.flashcard.flash_app.mapper.DeckMapper;
import com.flashcard.flash_app.repository.DeckRepository;
import com.flashcard.flash_app.repository.UserRepository;
import com.flashcard.flash_app.service.impl.DeckServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeckServiceImplTest {
    @Mock
    private DeckRepository deckRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeckMapper deckMapper;

//    @Spy
    @InjectMocks
    private DeckServiceImpl deckService;

    @Test
    void createDeckReturnDeckResponse() {
        // Arrange
        String userId = "1";
        DeckCreateRequest request = new DeckCreateRequest();
        User user = new User();
        user.setId(userId);
        Deck deck = new Deck();
        deck.setName("Animal");
        DeckResponse deckResponse = new DeckResponse();
        deckResponse.setName(deck.getName());

        when(deckMapper.toDeck(request)).thenReturn(deck);
        when(userRepository.getById(userId)).thenReturn(user);
        when(deckRepository.save(deck)).thenReturn(deck);
        when(deckMapper.toDeckResponse(deck)).thenReturn(deckResponse);

        // Act
        DeckResponse result = deckService.createDeck(userId, request);

        // Assert
        assertEquals(deck.getName(), result.getName());

        // Verify interactions
        verify(deckRepository, times(1)).save(deck);
    }

    @Test
    void getDeckByIdWhenDeckNotFoundThrowRunTimeException() {
        String userId = "1";
        String deckId = "2";

        when(deckRepository.getDeckById(deckId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deckService.getDeckById(userId, deckId));
        assertEquals("Deck not found", exception.getMessage());

        verify(deckRepository, times(1)).getDeckById(deckId);
        verify(deckMapper, never()).toDeckResponse(any(Deck.class));
        verify(deckService, never()).isAuthorized(anyString(), anyString());
    }

    @Test
    void getDeckByIdWhenDeckNotBelongDeckThrowRunTimeException() {
        String userId = "1";
        String deckId = "2";
        Deck deck = new Deck();
        deck.setName("Animal");
        deck.setStatus(0);

        when(deckRepository.getDeckById(deckId)).thenReturn(Optional.of(deck));
        doReturn(false).when(deckService).isAuthorized(userId, deckId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deckService.getDeckById(userId, deckId));
        assertEquals("Not authorized", exception.getMessage());

        verify(deckRepository, times(1)).getDeckById(deckId);
        verify(deckMapper, never()).toDeckResponse(any(Deck.class));
        verify(deckService, times(1)).isAuthorized(userId, deckId);
    }

    @Test
    void getDeckByIdReturnDeckResponse() {
        // Arrange
        String userId = "1";
        String deckId = "101";

        Deck deck = new Deck();
        deck.setId(deckId);
        deck.setStatus(0);  // Deck is private

        DeckResponse deckResponse = new DeckResponse();
        deckResponse.setId(deckId);

        when(deckRepository.getDeckById(deckId)).thenReturn(Optional.of(deck));
        doReturn(true).when(deckService).isAuthorized(userId, deckId);  // Authorized
        when(deckMapper.toDeckResponse(deck)).thenReturn(deckResponse);

        // Act
        DeckResponse result = deckService.getDeckById(userId, deckId);

        // Assert
        assertNotNull(result);
        assertEquals(deckId, result.getId());

        // Verify interactions
        verify(deckRepository, times(1)).getDeckById(deckId);
        verify(deckService, times(1)).isAuthorized(userId, deckId);
        verify(deckMapper, times(1)).toDeckResponse(deck);
    }

    @Test
    void getAllDecksReturnListDeckResponse() {
        String userId = "1";
        User user = new User();

        Deck deck1 = new Deck();
        deck1.setId("1");
        Deck deck2 = new Deck();
        deck2.setId("2");
        List<Deck> listDecks = Arrays.asList(deck1, deck2);

        DeckResponse deckResponse1 = new DeckResponse();
        deckResponse1.setId(deck1.getId());
        DeckResponse deckResponse2 = new DeckResponse();
        deckResponse2.setId(deck2.getId());

        when(userRepository.getById(userId)).thenReturn(user);
        when(deckRepository.findDecksByUser(user)).thenReturn(listDecks);
        when(deckMapper.toDeckResponses(listDecks)).thenReturn(Arrays.asList(deckResponse1, deckResponse2));

        List<DeckResponse> result = deckService.getAllDecks(userId);
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());

        verify(deckMapper, times(1)).toDeckResponses(listDecks);
    }

    @Test
    void updateDeckByIdWhenDeckNotBelongDeckThrowRunTimeException() {
        String userId = "1";
        String deckId = "2";
        DeckUpdateRequest request = new DeckUpdateRequest();

        doReturn(false).when(deckService).isAuthorized(userId, deckId);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> deckService.updateDeck(userId, deckId, request));
        assertEquals("Not authorized", exception.getMessage());

        verify(deckRepository, never()).getDeckById(deckId);
        verify(deckMapper, never()).updateDeck(any(Deck.class), any(DeckUpdateRequest.class));
        verify(deckMapper, never()).toDeckResponse(any(Deck.class));
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void updateDeckByIdWhenDeckNotFoundThrowRunTimeException() {
        String userId = "1";
        String deckId = "2";
        DeckUpdateRequest request = new DeckUpdateRequest();

        doReturn(true).when(deckService).isAuthorized(userId, deckId);
        when(deckRepository.getDeckById(deckId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> deckService.updateDeck(userId, deckId, request));
        assertEquals("Deck not found", exception.getMessage());

        verify(deckRepository, times(1)).getDeckById(deckId);
        verify(deckMapper, never()).updateDeck(any(Deck.class), any(DeckUpdateRequest.class));
        verify(deckMapper, never()).toDeckResponse(any(Deck.class));
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void updateDeckByIdReturnDeckResponse() {
        String userId = "1";
        String deckId = "2";
        DeckUpdateRequest request = new DeckUpdateRequest();
        Deck deck = new Deck();
        deck.setId("1");
        DeckResponse deckResponse = new DeckResponse();
        deckResponse.setId("1");

        doReturn(true).when(deckService).isAuthorized(userId, deckId);
        when(deckRepository.getDeckById(deckId)).thenReturn(Optional.of(deck));
        when(deckRepository.save(deck)).thenReturn(deck);
        when(deckMapper.toDeckResponse(deck)).thenReturn(deckResponse);

        DeckResponse result = deckService.updateDeck(userId, deckId, request);

        assertEquals(deck.getId(), result.getId());

        verify(deckRepository, times(1)).getDeckById(deckId);
        verify(deckMapper, times(1)).updateDeck(any(Deck.class), any(DeckUpdateRequest.class));
        verify(deckMapper, times(1)).toDeckResponse(any(Deck.class));
        verify(deckRepository, times(1)).save(any(Deck.class));
    }

    @Test
    void deleteDeckWhenDeckNotBelongDeckThrowRunTimeException() {
        String userId = "1";
        String deckId = "2";

        doReturn(false).when(deckService).isAuthorized(userId, deckId);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> deckService.deleteDeck(userId, deckId));
        assertEquals("Not authorized", exception.getMessage());

        verify(deckRepository, never()).deleteById(deckId);
    }

    @Test
    void deleteDeckTest() {
        String userId = "1";
        String deckId = "2";

        doReturn(true).when(deckService).isAuthorized(userId, deckId);

        deckService.deleteDeck(userId, deckId);
        verify(deckRepository, times(1)).deleteById(deckId);
    }

    @Test
    void isAuthorizedWhenDeckNotFoundThrowRunTimeException() {
        String userId = "1";
        String deckId = "2";

        when(deckRepository.getDeckById(deckId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> deckService.isAuthorized(userId, deckId));
        assertEquals("Deck not found", exception.getMessage());
    }

    @Test
    void isAuthorizedReturnBooleanValue() {
        String userId = "1";
        String deckId = "2";
        User user = new User();
        user.setId("1");
        Deck deck = new Deck();
        deck.setUser(user);

        when(deckRepository.getDeckById(deckId)).thenReturn(Optional.of(deck));
        boolean result = deckService.isAuthorized(userId, deckId);
        assertEquals(true, result);
    }
}
