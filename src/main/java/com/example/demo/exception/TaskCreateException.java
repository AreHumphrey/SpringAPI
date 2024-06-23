package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class TaskCreateException extends ApiException {
    public TaskCreateException(String message){
        super(HttpStatus.BAD_REQUEST,message);
    }
}
