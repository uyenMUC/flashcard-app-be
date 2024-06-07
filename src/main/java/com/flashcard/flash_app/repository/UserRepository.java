package com.flashcard.flash_app.repository;

import com.flashcard.flash_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsUsersByUsername(String username);
    boolean existsUsersByEmail(String email);
}
