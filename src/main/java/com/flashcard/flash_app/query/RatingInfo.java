package com.flashcard.flash_app.query;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingInfo {
    Long numberOfRatings;
    Long totalRatings;

    public RatingInfo(Long numberOfRatings, Long totalRatings) {
        this.numberOfRatings = numberOfRatings;
        this.totalRatings = totalRatings;
    }
}
