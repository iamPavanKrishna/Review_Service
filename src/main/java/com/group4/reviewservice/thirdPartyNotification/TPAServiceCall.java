package com.group4.reviewservice.thirdPartyNotification;

import com.group4.reviewservice.dtos.requests.NotificationRequest;
import com.group4.reviewservice.dtos.responses.NotificationResponse;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TPAServiceCall {

    private RestTemplate restTemplate;

    public TPAServiceCall(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }
    public NotificationResponse sendnotification(NotificationRequest notificationRequest, String url) {

        ResponseEntity<NotificationResponse> response = restTemplate.postForEntity(url, notificationRequest, NotificationResponse.class);

        return response.getBody();
    }
}
