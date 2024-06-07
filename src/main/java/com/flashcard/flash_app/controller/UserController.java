package com.flashcard.flash_app.controller;

import com.flashcard.flash_app.dto.request.ApiResponse;
import com.flashcard.flash_app.dto.request.UserCreationRequest;
import com.flashcard.flash_app.dto.request.UserUpdateRequest;
import com.flashcard.flash_app.entity.User;
import com.flashcard.flash_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{user_id}")
    User getUser(@PathVariable String user_id) {
        return userService.getUser(user_id);
    }

    @PutMapping("/{user_id}")
    User updateUser(@PathVariable String user_id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(user_id, request);
    }

    @DeleteMapping("/{user_id}")
    String deleteUser(@PathVariable String user_id) {
        userService.deleteUser(user_id);
        return "User has been deleted!";
    }

}
