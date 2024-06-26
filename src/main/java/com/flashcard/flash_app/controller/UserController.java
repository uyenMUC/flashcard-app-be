package com.flashcard.flash_app.controller;

import ch.qos.logback.classic.Logger;
import com.flashcard.flash_app.dto.request.ApiResponse;
import com.flashcard.flash_app.dto.request.UserCreationRequest;
import com.flashcard.flash_app.dto.request.UserUpdateRequest;
import com.flashcard.flash_app.dto.response.DeckResponse;
import com.flashcard.flash_app.dto.response.UserResponse;
import com.flashcard.flash_app.entity.User;
import com.flashcard.flash_app.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/signup")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping("/all")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{user_id}")
    UserResponse getUser(@PathVariable String user_id) {
        return userService.getUser(user_id);
    }

    @GetMapping()
    ApiResponse<UserResponse> getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String user_id = oauthToken.getToken().getClaim("user_id");
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUser(user_id));
        return apiResponse;
    }

    @PutMapping()
    ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String user_id = oauthToken.getToken().getClaim("user_id");
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(user_id, request));
        return apiResponse;
    }

    @DeleteMapping()
    String deleteUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String user_id = oauthToken.getToken().getClaim("user_id");
        userService.deleteUser(user_id);
        return "User has been deleted!";
    }

}
