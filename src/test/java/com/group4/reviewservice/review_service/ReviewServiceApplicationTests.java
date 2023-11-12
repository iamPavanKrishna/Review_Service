package com.group4.reviewservice.review_service;


import com.group4.reviewservice.ThirdPartyServices.NotificationServiceCallImpl;
import com.group4.reviewservice.enums.ReactionTypeEnum;
import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.models.UserReaction;
import com.group4.reviewservice.repos.*;
import com.group4.reviewservice.services.ReviewServiceImpl;
import com.group4.reviewservice.exceptions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {


}

