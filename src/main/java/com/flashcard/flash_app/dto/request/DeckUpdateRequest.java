package com.flashcard.flash_app.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeckUpdateRequest {
    String name;
    String description;
    int status;
    LocalDateTime updated_at = LocalDateTime.now();
}
