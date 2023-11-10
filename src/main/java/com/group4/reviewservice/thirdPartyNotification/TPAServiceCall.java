package com.group4.reviewservice.thirdPartyNotification;

import com.group4.reviewservice.dtos.NotificationRequestDto;
import com.group4.reviewservice.dtos.NotificationResponseDto;
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
    public NotificationResponseDto sendnotification(NotificationRequestDto notificationRequestDto, String url) {

        ResponseEntity<NotificationResponseDto> response = restTemplate.postForEntity(url, notificationRequestDto, NotificationResponseDto.class);  // TODO  restTemplate.exchange

        System.out.println("dafsjkfktdyuu ");

        return response.getBody();



    }
}
