package com.flashcard.flash_app.controller;

import com.flashcard.flash_app.dto.request.*;
import com.flashcard.flash_app.dto.response.CardResponse;
import com.flashcard.flash_app.service.CardService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardController {
    CardService cardService;

    @PostMapping
    ApiResponse<CardResponse> createCard(@RequestBody @Valid CardCreateRequest request) {
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cardService.createCard(request));
        return apiResponse;
    }

    @GetMapping("cardId/{cardId}")
    ApiResponse<CardResponse> getDeckById(@PathVariable String cardId) {
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cardService.getCardById(cardId));
        return apiResponse;
    }

    @GetMapping("deckId/{deckId}")
    ApiResponse<List<CardResponse>> getAllDecks(@PathVariable String deckId) {
        ApiResponse<List<CardResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cardService.getAllCards(deckId));
        return apiResponse;
    }

    @PutMapping("/{cardId}")
    ApiResponse<CardResponse> updateDeck(@PathVariable String cardId, @RequestBody CardUpdateRequest request) {
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cardService.updateCard(cardId, request));
        return apiResponse;
    }

    @DeleteMapping("/{cardId}")
    ApiResponse deleteUser(@PathVariable String cardId) {
        cardService.deleteCard(cardId);
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();
        return apiResponse.builder()
                .code(1000)
                .message("Card has been deleted!")
                .build();
    }
}
