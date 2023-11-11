package com.group4.reviewservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

// This ExceptionDto is a Data Transfer Object for Exception Handling
@Getter
@Setter
public class ExceptionDto {
    private HttpStatus errorCode;
    private String message;

    public ExceptionDto(HttpStatus status, String message) {
        this.errorCode = status;
        this.message = message;
    }
}
