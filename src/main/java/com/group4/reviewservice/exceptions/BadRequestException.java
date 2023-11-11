package com.group4.reviewservice.exceptions;

// This method is to handle BadRequestException
public class BadRequestException extends Exception {
    public BadRequestException(String s) {
        super(s);
    }
}
