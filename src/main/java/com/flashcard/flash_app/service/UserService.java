package com.flashcard.flash_app.service;

import com.flashcard.flash_app.dto.request.UserCreationRequest;
import com.flashcard.flash_app.dto.request.UserUpdateRequest;
import com.flashcard.flash_app.dto.response.UserResponse;
import com.flashcard.flash_app.entity.User;

import java.util.List;

public interface UserService {
    public UserResponse createUser(UserCreationRequest request);
    public List<User> getAllUsers();
    public UserResponse getUser(String userId);
    public UserResponse updateUser(String userId, UserUpdateRequest request);
    public void deleteUser(String userId);
}
