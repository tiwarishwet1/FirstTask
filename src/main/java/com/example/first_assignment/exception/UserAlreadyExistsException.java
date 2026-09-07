package com.example.first_assignment.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("Username '" + username + "' already exists");
    }
}