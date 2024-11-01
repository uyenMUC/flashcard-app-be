package com.flashcard.flash_app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.flashcard.flash_app.dto.request.UserCreationRequest;
import com.flashcard.flash_app.dto.request.UserUpdateRequest;
import com.flashcard.flash_app.dto.response.UserResponse;
import com.flashcard.flash_app.entity.User;
import com.flashcard.flash_app.exception.AppException;
import com.flashcard.flash_app.exception.ErrorCode;
import com.flashcard.flash_app.mapper.UserMapper;
import com.flashcard.flash_app.repository.UserRepository;
import com.flashcard.flash_app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserWhenUsernameExistsShouldThrowUserExistedException() {
        UserCreationRequest request = new UserCreationRequest();
        request.setUsername("existingUser");
        request.setEmail("user@example.com");
        request.setPassword("password123");

        when(userRepository.existsUsersByUsername(request.getUsername())).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> userService.createUser(request));
        assertEquals(ErrorCode.USER_EXISTED, exception.getErrorCode());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUserWhenEmailExistsShouldThrowEmailExistedException() {
        UserCreationRequest request = new UserCreationRequest();
        request.setUsername("newUser");
        request.setEmail("existing@example.com");
        request.setPassword("password123");

        when(userRepository.existsUsersByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsUsersByEmail(request.getEmail())).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> userService.createUser(request));
        assertEquals(ErrorCode.EMAIL_EXISTED, exception.getErrorCode());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUserWhenValidRequestShouldCreateAndReturnUserResponse() {
        UserCreationRequest request = new UserCreationRequest();
        request.setUsername("newUser");
        request.setEmail("new@example.com");
        request.setPassword("password123");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());

        when(userRepository.existsUsersByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsUsersByEmail(request.getEmail())).thenReturn(false);
        when(userMapper.toUser(request)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(userResponse.getUsername(), result.getUsername());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        assertTrue(passwordEncoder.matches(request.getPassword(), user.getPassword()));

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getAllUsersReturnListUser() {
        User user1 = new User();
        user1.setId("1");

        User user2 = new User();
        user2.setId("2");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<User> result = userService.getAllUsers();

        assertEquals(Arrays.asList(user1, user2), result);
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserSuccessfully() {
        String userId = "1";
        User user = new User();
        user.setUsername("username");

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getUser(userId);
        assertEquals(userResponse.getUsername(), result.getUsername());

        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toUserResponse(user);
    }

    @Test
    void getUserNotFoundThrowException() {
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUser(userId));
        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).toUserResponse(any(User.class));
    }

    @Test
    void updateUserReturnUserResponse() {
        String userId = "1";
        UserUpdateRequest request = new UserUpdateRequest();
        request.setUsername("newUsername");

        User user = new User();
        user.setUsername("oldUsername");

        UserResponse updateUserResponse = new UserResponse();
        updateUserResponse.setUsername("newUsername");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(updateUserResponse);
        UserResponse result = userService.updateUser(userId, request); assertEquals(request.getUsername(), result.getUsername());
    }

    @Test
    void updateUserWhenUserNotFoundThrowRunTimeException() {
        String userId = "1";
        UserUpdateRequest request = new UserUpdateRequest();
        request.setUsername("newUsername");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> userService.updateUser(userId, request));
        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).updateUser(any(User.class), any(UserUpdateRequest.class));
        verify(userMapper, never()).toUserResponse(any(User.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUserTest() {
        String userId = "1";
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }
}

