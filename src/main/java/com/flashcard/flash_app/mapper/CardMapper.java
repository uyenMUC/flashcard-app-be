package com.flashcard.flash_app.mapper;

import com.flashcard.flash_app.dto.request.*;
import com.flashcard.flash_app.dto.response.CardResponse;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.entity.Card;
import com.flashcard.flash_app.entity.Deck;
import com.flashcard.flash_app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CardMapper {
    Card toCard(CardCreateRequest request);
    CardResponse toCardResponse(Card card);
    List<CardResponse> toCardResponses(List<Card> cards);
    void updateCard(@MappingTarget Card card, CardUpdateRequest request);
}
