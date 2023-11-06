package com.group4.reviewservice.services;

import com.group4.reviewservice.exceptions.*;
import com.group4.reviewservice.models.Review;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    public Review submitReview(Review review) 
            throws NotFoundException, TPAServiceException, InternalServerException, BadRequestException;

    public List<Review> getAllReviews()   
            throws NotFoundException, TPAServiceException, InternalServerException;

    List<Review> getAllReviewsByServiceId(UUID id)   
            throws NotFoundException, TPAServiceException, InternalServerException;

    List<Review> getAllReviewsByUserId(UUID id)   
            throws NotFoundException, TPAServiceException, InternalServerException;

    public Optional<Review> getReviewById(String id) 
            throws NotFoundException, InternalServerException;

    public Review updateReview(Review review)   
            throws NotFoundException, InternalServerException, BadRequestException;

    public void deleteReview(String id)  
            throws NotFoundException, InternalServerException;
}