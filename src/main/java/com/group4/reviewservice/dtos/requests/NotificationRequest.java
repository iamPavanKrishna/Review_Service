package com.group4.reviewservice.dtos.requests;

public record NotificationRequest(String service_id, String data, String image_url, String channel, String type, String subject) {

}
