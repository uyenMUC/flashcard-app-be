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
        String user_id = oauthToken.getToken().getClaim("user_id");
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.createDeck(user_id, request));
        return apiResponse;
    }

    @GetMapping("/{deck_id}")
    ApiResponse<DeckResponse> getDeckById(@PathVariable String deck_id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String user_id = oauthToken.getToken().getClaim("user_id");
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.getDeckById(user_id, deck_id));
        return apiResponse;
    }

    @GetMapping("/all")
    ApiResponse<List<DeckResponse>> getAllDecks() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String user_id = oauthToken.getToken().getClaim("user_id");
        ApiResponse<List<DeckResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.getAllDecks(user_id));
        return apiResponse;
    }

    @PutMapping("/{deck_id}")
    ApiResponse<DeckResponse> updateDeck(@PathVariable String deck_id, @RequestBody DeckUpdateRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String user_id = oauthToken.getToken().getClaim("user_id");
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deckService.updateDeck(user_id, deck_id, request));
        return apiResponse;
    }

    @DeleteMapping("/{deck_id}")
    ApiResponse deleteUser(@PathVariable String deck_id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String user_id = oauthToken.getToken().getClaim("user_id");
        deckService.deleteDeck(user_id, deck_id);
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        return apiResponse.builder()
                .code(1000)
                .message("Deck has been deleted!")
                .build();
    }
}
