package com.group4.reviewservice.dtos.responses;

// This NotificationRequest is a record which acts as a Data Transfer Object for the response body for the Third Party Notification Service
public record NotificationResponse(String message, String service_id) {

}
