package com.flashcard.flash_app.exception;

public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error"),
    USER_EXISTED (1001, "Username already exists"),
    EMAIL_EXISTED (1002, "Email already exists"),
    PASSWORD_INVALID (1003, "Password must be at least 8 characters"),
    USERNAME_EMPTY (1004, "Username cannot be empty"),
    EMAIL_INVALID (1005, "Email is not valid"),
    EMAIL_EMPTY (1006, "Email cannot be empty"),
    KEY_INVALID (1007, "Key error is not valid"),
    USER_NOT_EXISTED(1008, "User not exists"),
    UNAUTHENTICATED(1009, "Unauthenticated error"),
    DECK_NAME_EMPTY(1010, "Deck name cannot be empty"),
    RATE_DECK_PRIVATE(1011, "Cannot rate deck private"),
    ;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
