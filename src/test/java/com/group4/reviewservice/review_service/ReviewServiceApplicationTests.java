package com.group4.reviewservice.review_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.group4.reviewservice.exceptions.*;
import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.repos.ReviewRepository;
import com.group4.reviewservice.services.ReviewServiceImpl;
import com.group4.reviewservice.thirdPartyNotification.TPAServiceCall;

import io.swagger.v3.oas.annotations.media.Content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {
    @Test
	void contextLoads() {
	}
}

