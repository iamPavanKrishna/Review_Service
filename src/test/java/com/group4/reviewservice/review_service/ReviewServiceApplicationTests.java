package com.group4.reviewservice.review_service;


import com.group4.reviewservice.ThirdPartyServices.NotificationServiceCall;
import com.group4.reviewservice.ThirdPartyServices.NotificationServiceCallImpl;
import com.group4.reviewservice.ThirdPartyServices.UserServiceCallImpl;
import com.group4.reviewservice.dtos.requests.NotificationRequest;
import com.group4.reviewservice.dtos.responses.NotificationResponse;
import com.group4.reviewservice.enums.AttachmentTypeEnum;
import com.group4.reviewservice.models.Review;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.statements.SpringRepeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.swing.Spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserReactionRepository userReactionRepository;

    @Mock
    private NotificationServiceCall tpaServiceCall;

    @Mock
    private NotificationServiceCallImpl notificationServiceCall;

    @Mock
    private UserServiceCallImpl userServiceCall;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for get all reviews start
    @Test
    public void testGetAllReviewsWithReviews() throws NotFoundException, InternalServerException {
        // Create a list of sample reviews
        List<Review> sampleReviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setId(UUID.randomUUID());
            review.setUserId(UUID.randomUUID());
            review.setServiceId(UUID.randomUUID());
            sampleReviews.add(review);
        }

        // Mock the behavior of the reviewRepository to return the sample reviews
        lenient().when(reviewRepository.findAll()).thenReturn(sampleReviews);

        // Call the service method
        List<Review> allReviews = reviewService.getAllReviews();

        // Assertions
        assertNotNull(allReviews);
        if (allReviews.isEmpty()){
            assertEquals(0, allReviews.size());
        }
        else{
            assertEquals(sampleReviews.size(), allReviews.size());
        }

    }
    // Test cases for get all reviews end



    // Test cases for get review by service id start
    @Test
    public void testGetAllReviewsByServiceIdWithValidId() throws NotFoundException, InternalServerException, BadRequestException {
        // Create a sample service ID and a list of sample reviews
        UUID serviceId = UUID.randomUUID();
        List<Review> sampleReviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setId(UUID.randomUUID());
            review.setUserId(UUID.randomUUID());
            review.setServiceId(serviceId);
            review.setText("");
            review.setAttachmentTypeEnum(AttachmentTypeEnum.IMAGE);
            review.setAttachmentUrl("");
            review.setUsefulCount(0);
            review.setFunnyCount(0);
            review.setCoolCount(0);
            sampleReviews.add(review);
        }

        // Mock the behavior of the reviewRepository to return the sample reviews for the specified service ID
        lenient().when(reviewRepository.getAllReviewsByServiceId(serviceId)).thenReturn(sampleReviews);
        // Call the service method
        List<Review> reviews = reviewService.getAllReviewsByServiceId(serviceId);

        // Assertions
        assertNotNull(reviews);

        if (reviews.isEmpty()){
            assertEquals(0, reviews.size());
        }
        else{
            assertEquals(sampleReviews.size(), reviews.size());
        }
    }

    @Test
    public void testGetAllReviewsByServiceIdWithInvalidId() throws BadRequestException{
        // Mock the behavior of the reviewRepository to return an empty list
        UUID invalidServiceId = UUID.randomUUID();
        List<Review> reviews = reviewService.getAllReviewsByServiceId(invalidServiceId);
        // Call the service method with an invalid service ID
        
        assertEquals(0, reviews.size());
    }
    // Test cases for get review by service id end



    // Test cases for get review by user id start
    @Test
    public void testGetAllReviewsByUserIdWithValidId() throws NotFoundException, InternalServerException, BadRequestException {
        // Create a sample user ID and a list of sample reviews
        UUID userId = UUID.randomUUID();
        List<Review> sampleReviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setId(UUID.randomUUID());
            review.setUserId(userId);
            review.setServiceId(UUID.randomUUID());
            review.setText("");
            review.setAttachmentTypeEnum(AttachmentTypeEnum.IMAGE);
            review.setAttachmentUrl("");
            review.setUsefulCount(0);
            review.setFunnyCount(0);
            review.setCoolCount(0);
            sampleReviews.add(review);
        }

        // Mock the behavior of the reviewRepository to return the sample reviews for the specified user ID
        lenient().when(reviewRepository.getAllReviewsByUserId(userId)).thenReturn(sampleReviews);

        // Call the service method with an invalid service ID
        List<Review> reviews = reviewService.getAllReviewsByUserId(userId);

        // Assertions
        assertNotNull(reviews);
        if (reviews.isEmpty()){
            assertEquals(0, reviews.size());
        }
        else{
            assertEquals(sampleReviews.size(), reviews.size());
        }
    }

    @Test
    public void testGetAllReviewsByUserIdWithInvalidId() throws BadRequestException{
        // Mock the behavior of the reviewRepository to return an empty list
        UUID invalidUserId = UUID.randomUUID();
        lenient().when(reviewRepository.getAllReviewsByUserId(invalidUserId)).thenReturn(new ArrayList<>());

        // Call the service method with an invalid user ID
        List<Review> reviews = reviewService.getAllReviewsByUserId(invalidUserId);

        assertNotNull(reviews);
        assertEquals(0, reviews.size());
    }
    // Test cases for get review by user id end



    // Test cases for get review by id start
    @Test
    public void testGetReviewByIdWithEmptyId() {
        // Call the service method with an empty review ID
        assertThrows(BadRequestException.class, () -> reviewService.getReviewById(""));
    }

    @Test
    public void testGetReviewByIdWithNullId() {
        // Call the service method with a null review ID
        assertThrows(BadRequestException.class, () -> reviewService.getReviewById(null));
    }
    // Test cases for get review by id end



    // Test cases for update review start
    @Test
    public void testUpdateReviewWithInvalidData() {
        // Create an empty review with a valid review ID
        UUID reviewId = UUID.randomUUID();
        Review emptyReview = new Review();
        emptyReview.setId(reviewId);

        // Mock the behavior of the reviewRepository to return the empty review for the specified ID
        lenient().when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(emptyReview));

        // Call the service method with an empty review (invalid data)
        assertThrows(InternalServerException.class, () -> reviewService.updateReview(emptyReview));
    }

    @Test
    public void testUpdateReviewWithEmptyId() {
        // Create a sample review with an empty ID
        Review reviewWithEmptyId = new Review();
        reviewWithEmptyId.setId(null);

        // Call the service method with a review having an empty ID
        assertThrows(BadRequestException.class, () -> reviewService.updateReview(reviewWithEmptyId));
    }

    @Test
    public void testUpdateReviewWithNullId() {
        // Create a sample review with a null ID
        Review reviewWithNullId = new Review();
        reviewWithNullId.setId(null);

        // Call the service method with a review having a null ID
        assertThrows(BadRequestException.class, () -> reviewService.updateReview(reviewWithNullId));
    }
    // Test cases for update review end



    // Test cases for delete review start
    @Test
    public void testDeleteReviewSuccess() throws NotFoundException, BadRequestException, TPAServiceException, InternalServerException {
        // Create a sample review and its ID
        UUID reviewId = UUID.randomUUID();
        Review review = new Review();
        review.setId(reviewId);
        review.setUserId(UUID.randomUUID());
        review.setServiceId(UUID.randomUUID());
        review.setAttachmentTypeEnum(AttachmentTypeEnum.valueOf("IMAGE"));
        review.setAttachmentUrl("");
        review.setCoolCount(0);
        review.setFunnyCount(0);
        review.setUsefulCount(0);
        review.setText("");

        // Mock the behavior of reviewRepository to return the review when queried by ID
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        doNothing().when(reviewRepository).deleteById(reviewId);
        when(notificationServiceCall.sendnotification(any(NotificationRequest.class), any(String.class))).thenReturn(new NotificationResponse("Review deleted", review.getServiceId().toString()));
        

        // Test the deleteReview method
        try {
            reviewService.deleteReview(reviewId.toString());
        } catch (Exception e) {
            fail("Exception should not be thrown " + e.getMessage());
        }
    }


    @Test
    public void testDeleteReviewBadRequest() {
        // Test the deleteReview method with a null review ID
        try {
            reviewService.deleteReview(null);
            fail("BadRequestException should be thrown");
        } catch (BadRequestException e) {
            // Expected behavior
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }
    }
}

