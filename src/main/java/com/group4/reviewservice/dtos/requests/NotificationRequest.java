package com.group4.reviewservice.dtos.requests;

// This NotificationRequest is a record which acts as a Data Transfer Object for the input request body for the Third Party Notification Service
public record NotificationRequest(String service_id, String data, String image_url, String channel, String type, String subject) {

}
