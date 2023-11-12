package com.group4.reviewservice.services;

import com.group4.reviewservice.enums.ReactionTypeEnum;
import com.group4.reviewservice.exceptions.*;
import com.group4.reviewservice.models.Review;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;


// This is an Interface for the Service Calls
@Service
public interface ReviewService {

        // This is an abstract method for submitting a review
        public Review submitReview(Review review) 
                throws NotFoundException, TPAServiceException, InternalServerException, BadRequestException;

        // This is an abstract method to fetch all the reviews
        public List<Review> getAllReviews()   
                throws NotFoundException, InternalServerException;

        // This is an abstract method to fetch all the reviews by serviceId
        List<Review> getAllReviewsByServiceId(UUID id)
                throws BadRequestException;

        // This is an abstract method to fetch all the reviews by userId
        List<Review> getAllReviewsByUserId(UUID id)   
                throws BadRequestException;

        // This is an abstract method to fetch a review  by id
        public Optional<Review> getReviewById(String id) 
                throws BadRequestException, InternalServerException;

        // This is an abstract method for updating a review
        public Review updateReview(Review review)   
                throws InternalServerException, BadRequestException, TPAServiceException;

        // This is an abstract method for deleting a review
        public String deleteReview(String id)
                throws BadRequestException, TPAServiceException, InternalServerException;

        // This is an abstract method for adding a reaction to the review
        public void reactToReview(UUID review, ReactionTypeEnum reactionTypeEnum, UUID userId) 
                throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException;
        
}