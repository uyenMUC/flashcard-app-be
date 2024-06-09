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

    @PostMapping("/{user_id}/{deck_id}")
    ApiResponse<DeckResponse> rateDeck(@PathVariable("user_id") String user_id,
                                       @PathVariable("deck_id") String deck_id,
                                       @RequestBody RatingRequest request) {
        ApiResponse<DeckResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(ratingService.rateDeck(user_id, deck_id, request));
        return apiResponse;
    }
}
