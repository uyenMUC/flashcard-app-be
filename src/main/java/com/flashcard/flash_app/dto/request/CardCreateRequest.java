package com.flashcard.flash_app.dto.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardCreateRequest {
    String deck_id;
    String front_text;
    String back_text;
    String image = null;
    LocalDateTime created_at = LocalDateTime.now();
    LocalDateTime updated_at = LocalDateTime.now();
}
