package com.flashcard.flash_app.repository;

import com.flashcard.flash_app.entity.Deck;
import com.flashcard.flash_app.entity.Rating;
import com.flashcard.flash_app.entity.User;
import com.flashcard.flash_app.query.RatingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
    Rating findByDeckAndUser(Deck deck, User user);
    @Query(value = """
       select 
        new com.flashcard.flash_app.query.RatingInfo(count(*), sum(r.rateValue))
        from Rating r
        where r.deck=:deck
        group by r.deck
        """)
    RatingInfo countTotalRatingsByDeck(@Param("deck") Deck deck);
}
