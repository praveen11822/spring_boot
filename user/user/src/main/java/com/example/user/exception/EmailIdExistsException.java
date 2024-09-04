package com.example.user.exception;

public class EmailIdExistsException extends RuntimeException {
    public EmailIdExistsException(String message) {
        super(message);
    }
}
