package com.group4.reviewservice.ThirdPartyServices;

import com.group4.reviewservice.dtos.responses.UserResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceCallImpl implements UserServiceCall{

    private RestTemplate restTemplate;

    @Value("${USERSERVICE_URL}")
    private String baseUserServiceUrl;

    public UserServiceCallImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }
    public UserResponse getUserDetails(String userId) {
        String url = baseUserServiceUrl + "/api/v1/" + userId;
        System.out.println(baseUserServiceUrl);
        ResponseEntity<UserResponse> response = restTemplate.getForEntity(url, UserResponse.class);

        return response.getBody();
    }
}
