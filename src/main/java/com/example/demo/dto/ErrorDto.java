package com.example.demo.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDto {
    private String message;
}
