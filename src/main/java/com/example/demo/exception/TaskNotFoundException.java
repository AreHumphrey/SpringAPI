package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends ApiException{
    public TaskNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
