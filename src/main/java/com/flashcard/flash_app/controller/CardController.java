package com.flashcard.flash_app.controller;

import com.flashcard.flash_app.dto.request.*;
import com.flashcard.flash_app.dto.response.CardResponse;
import com.flashcard.flash_app.dto.response.DeckResponse;
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

    @GetMapping("card_id/{card_id}")
    ApiResponse<CardResponse> getDeckById(@PathVariable String card_id) {
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cardService.getCardById(card_id));
        return apiResponse;
    }

    @GetMapping("deck_id/{deck_id}")
    ApiResponse<List<CardResponse>> getAllDecks(@PathVariable String deck_id) {
        ApiResponse<List<CardResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cardService.getAllCards(deck_id));
        return apiResponse;
    }

    @PutMapping("/{card_id}")
    ApiResponse<CardResponse> updateDeck(@PathVariable String card_id, @RequestBody CardUpdateRequest request) {
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cardService.updateCard(card_id, request));
        return apiResponse;
    }

    @DeleteMapping("/{card_id}")
    ApiResponse deleteUser(@PathVariable String card_id) {
        cardService.deleteCard(card_id);
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();
        return apiResponse.builder()
                .code(1000)
                .message("Card has been deleted!")
                .build();
    }
}
