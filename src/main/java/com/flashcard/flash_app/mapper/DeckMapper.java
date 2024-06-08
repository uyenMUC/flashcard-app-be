package com.flashcard.flash_app.mapper;

import com.flashcard.flash_app.dto.request.DeckCreateRequest;
import com.flashcard.flash_app.dto.request.DeckUpdateRequest;
import com.flashcard.flash_app.dto.request.UserCreationRequest;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.entity.Deck;
import com.flashcard.flash_app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DeckMapper {
    Deck toDeck(DeckCreateRequest request);
    DeckResponse toDeckResponse(Deck deck);
    List<DeckResponse> toDeckResponses(List<Deck> decks);
    void updateDeck(@MappingTarget Deck deck, DeckUpdateRequest request);
}
