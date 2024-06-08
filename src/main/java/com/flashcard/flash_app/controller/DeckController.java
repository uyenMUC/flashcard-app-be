package com.flashcard.flash_app.controller;

import com.flashcard.flash_app.dto.request.*;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.dto.response.UserResponse;
import com.flashcard.flash_app.entity.Deck;
import com.flashcard.flash_app.service.DeckService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeckController {

    DeckService deckService;

    @PostMapping
    ApiResponse<DeckResponse> createDeck(@RequestBody @Valid DeckCreateRequest request) {
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.createDeck(request));
        return apiResponse;
    }

    @GetMapping("deck_id/{deck_id}")
    ApiResponse<DeckResponse> getDeckById(@PathVariable String deck_id) {
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.getDeckById(deck_id));
        return apiResponse;
    }

    @GetMapping("user_id/{user_id}")
    ApiResponse<List<DeckResponse>> getAllDecks(@PathVariable String user_id) {
        ApiResponse<List<DeckResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.getAllDecks(user_id));
        return apiResponse;
    }

//    @PostMapping("rating/{user_id}/{deck_id}")
//    ApiResponse<List<DeckResponse>> rateDeck(@PathVariable(name = "user_id") String user_id,
//                                             @PathVariable(name = "deck_id") String deck_id) {
//        ApiResponse<List<DeckResponse>> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(deckService.rateDeck(user_id, deck_id));
//        return apiResponse;
//    }

    @PutMapping("/{deck_id}")
    DeckResponse updateDeck(@PathVariable String deck_id, @RequestBody DeckUpdateRequest request) {
        return deckService.updateDeck(deck_id, request);
    }

    @DeleteMapping("/{deck_id}")
    String deleteUser(@PathVariable String deck_id) {
        deckService.deleteDeck(deck_id);
        return "Deck has been deleted!";
    }
}
