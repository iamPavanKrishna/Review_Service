package com.group4.reviewservice.exceptions;

// This method is to handle InternalServerException
public class InternalServerException extends Exception{
    public InternalServerException(String message) {
        super(message);
    }
}
