package com.flashcard.flash_app.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardUpdateRequest {
    String frontText;
    String backText;
    LocalDateTime updatedAt = LocalDateTime.now();
}
