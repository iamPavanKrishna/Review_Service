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
    public NotificationResponseDto sendnotification(NotificationRequestDto notificationRequestDto) {

        ResponseEntity<NotificationResponseDto> response = restTemplate.postForEntity("http://localhost:3000/notification/send/b4827950-9373-469c-bf60-e1d2cf14330f", notificationRequestDto, NotificationResponseDto.class);  // TODO  restTemplate.exchange

        System.out.println("dafsjkfktdyuu ");

        return response.getBody();



    }
}
