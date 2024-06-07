package com.flashcard.flash_app.exception;

public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error"),
    USER_EXSITED (1001, "Username already exists"),
    EMAIL_EXSITED (1002, "Email already exists"),
    PASSWORD_INVALID (1003, "Password must be at least 8 characters"),
    USERNAME_EMPTY (1004, "Username cannot be empty"),
    EMAIL_INVALID (1005, "Email is not valid"),
    EMAIL_EMPTY (1006, "Email cannot be empty"),
    KEY_INVALID (1007, "Key error is not valid"),
    ;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
