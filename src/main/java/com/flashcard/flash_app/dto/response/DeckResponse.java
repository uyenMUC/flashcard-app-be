package com.flashcard.flash_app.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeckResponse {
    String id;
    String name;
    int status;
    String description;
    float rate;
    int numOfRatings;
    LocalDateTime created_at;
    LocalDateTime updated_at;
}
