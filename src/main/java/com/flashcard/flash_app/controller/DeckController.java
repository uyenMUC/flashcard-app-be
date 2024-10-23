package com.flashcard.flash_app.controller;

import com.flashcard.flash_app.dto.request.*;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.service.DeckService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deck")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeckController {

    DeckService deckService;

    @PostMapping
    ApiResponse<DeckResponse> createDeck(@RequestBody @Valid DeckCreateRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String userId = oauthToken.getToken().getClaim("user_id");
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.createDeck(userId, request));
        return apiResponse;
    }

    @GetMapping("/{deckId}")
    ApiResponse<DeckResponse> getDeckById(@PathVariable String deckId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String userId = oauthToken.getToken().getClaim("user_id");
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.getDeckById(userId, deckId));
        return apiResponse;
    }

    @GetMapping("/all")
    ApiResponse<List<DeckResponse>> getAllDecks() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String userId = oauthToken.getToken().getClaim("user_id");
        ApiResponse<List<DeckResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.getAllDecks(userId));
        return apiResponse;
    }

    @PutMapping("/{deckId}")
    ApiResponse<DeckResponse> updateDeck(@PathVariable String deckId, @RequestBody DeckUpdateRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String userId = oauthToken.getToken().getClaim("user_id");
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.updateDeck(userId, deckId, request));
        return apiResponse;
    }

    @DeleteMapping("/{deckId}")
    ApiResponse deleteUser(@PathVariable String deckId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String userId = oauthToken.getToken().getClaim("user_id");
        deckService.deleteDeck(userId, deckId);
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        return apiResponse.builder()
                .code(1000)
                .message("Deck has been deleted!")
                .build();
    }
}
