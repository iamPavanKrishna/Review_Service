package com.group4.reviewservice.services;

import com.group4.reviewservice.enums.ReactionTypeEnum;
import com.group4.reviewservice.exceptions.*;
import com.group4.reviewservice.models.Review;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

        public Review submitReview(Review review) 
                throws NotFoundException, TPAServiceException, InternalServerException;

        public List<Review> getAllReviews()   
                throws NotFoundException, InternalServerException;

        List<Review> getAllReviewsByServiceId(UUID id)   
                throws NotFoundException, InternalServerException, BadRequestException;

        List<Review> getAllReviewsByUserId(UUID id)   
                throws NotFoundException, InternalServerException, BadRequestException;

        public Optional<Review> getReviewById(String id) 
                throws NotFoundException, InternalServerException, BadRequestException;

        public Review updateReview(Review review)   
                throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException;

        public void deleteReview(String id)  
                throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException;
        
        public void reactToReview(UUID review, ReactionTypeEnum reactionTypeEnum, UUID userId) 
                throws NotFoundException, InternalServerException, BadRequestException;
        
}