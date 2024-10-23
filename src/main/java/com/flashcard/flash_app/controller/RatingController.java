package com.flashcard.flash_app.controller;

import com.flashcard.flash_app.dto.request.ApiResponse;
import com.flashcard.flash_app.dto.request.RatingRequest;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.service.RatingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingController {

    RatingService ratingService;

    @PostMapping("/{userId}/{deckId}")
    ApiResponse<DeckResponse> rateDeck(@PathVariable("userId") String userId,
                                       @PathVariable("deckId") String deckId,
                                       @RequestBody RatingRequest request) {
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(ratingService.rateDeck(userId, deckId, request));
        return apiResponse;
    }
}
