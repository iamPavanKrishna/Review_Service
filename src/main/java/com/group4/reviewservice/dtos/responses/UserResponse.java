package com.group4.reviewservice.dtos.responses;

// This UserRequest is a record which acts as a Data Transfer Object for the input request body for the Third Party User Service
public record UserResponse (String userId,String email,String name) {

}
