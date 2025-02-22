package com.sean.todo.exception;

public class UpdateInvalidException extends RuntimeException {
    public UpdateInvalidException(String message) {
        super(message);
    }
}
