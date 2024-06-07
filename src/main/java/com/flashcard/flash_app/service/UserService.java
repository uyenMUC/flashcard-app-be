package com.flashcard.flash_app.service;

import com.flashcard.flash_app.dto.request.ApiResponse;
import com.flashcard.flash_app.dto.request.UserCreationRequest;
import com.flashcard.flash_app.dto.request.UserUpdateRequest;
import com.flashcard.flash_app.entity.User;
import com.flashcard.flash_app.exception.AppException;
import com.flashcard.flash_app.exception.ErrorCode;
import com.flashcard.flash_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request) {
        if (userRepository.existsUsersByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXSITED);
        }
        if (userRepository.existsUsersByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXSITED);
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String user_id) {
        return userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String user_id, UserUpdateRequest request) {
        User user = getUser(user_id);


        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUpdated_at(LocalDateTime.now());

        return userRepository.save(user);
    }

    public void deleteUser(String user_id) {
        userRepository.deleteById(user_id);
    }
}
