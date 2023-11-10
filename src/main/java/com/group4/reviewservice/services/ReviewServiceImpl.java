package com.group4.reviewservice.services;

import com.group4.reviewservice.dtos.NotificationRequestDto;
import com.group4.reviewservice.dtos.NotificationResponseDto;
import com.group4.reviewservice.exceptions.*;
import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.repos.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.group4.reviewservice.thirdPartyNotification.TPAServiceCall;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("ReviewService")
@Primary
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final TPAServiceCall tpaServiceCall;
    private String baseNotificationUrl = "http://notificationservice.eu-north-1.elasticbeanstalk.com/notification/send/";


    @Override
    public Review submitReview(Review review) 
            throws NotFoundException, TPAServiceException, InternalServerException {
                Review review2 = new Review();
                try {
                    review2 = reviewRepository.save(review);
                } catch (Exception e) {
                    throw new InternalServerException("Internal Server Error: Review Management Service is down...");
                }
                
                 NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
                 try {
                    notificationRequestDto.setService_id(review2.getServiceId().toString());
                    notificationRequestDto.setData("Successfully Integrated Review Service with Notification Service");
                    notificationRequestDto.setImage_url("https://logowik.com/content/uploads/images/mail-notification4831.jpg");
                    notificationRequestDto.setSubject("Review Service and Notification Service Integration");
                    notificationRequestDto.setType("alert");
                    notificationRequestDto.setChannel("email");
                
                 String url = baseNotificationUrl + review2.getUserId().toString();
                 System.out.println(url);
                 System.out.println(review2);

                 NotificationResponseDto notificationResponseDto = tpaServiceCall.sendnotification(notificationRequestDto, url);
                 System.out.println(notificationResponseDto);
                 } catch (Exception e) {
                    throw new TPAServiceException("Notification Service is down");
                 }

                 
                return review2;
    }

    @Override
    public List<Review> getAllReviews()   
            throws NotFoundException, TPAServiceException, InternalServerException {
            List<Review> review = new ArrayList<>();
            try {
                reviewRepository.findAll().forEach(review::add);
            } catch (Exception e) {
                throw new InternalServerException("Internal Server Error: Review Management Service is down...");
            }

            return review;
    }

    @Override
    public List<Review> getAllReviewsByServiceId(UUID id)    
        throws NotFoundException, TPAServiceException, InternalServerException, BadRequestException {
        if (id == null) {
                throw new BadRequestException("Review ID is required");
            }
        List <Review> review = new ArrayList<>();
        try{
            review = reviewRepository.getAllReviewsByServiceId(id);
        } catch (Exception e) {
            throw new InternalServerException("Internal Server Error: Review Management Service is down...");
        }
        return  review;

    }

    @Override
    public List<Review> getAllReviewsByUserId(UUID id)    
            throws NotFoundException, TPAServiceException, InternalServerException, BadRequestException {
            if (id == null) {
                throw new BadRequestException("Review ID is required");
            }
            List <Review> review = new ArrayList<>();
            try{
                review = reviewRepository.getAllReviewsByUserId(id);
            } catch (Exception e) {
                throw new InternalServerException("Internal Server Error: Review Management Service is down...");
            }  
            return  review;

    }

    @Override
    public Optional<Review> getReviewById(String id) 
            throws NotFoundException, InternalServerException, BadRequestException {
            if (id == null) {
                throw new BadRequestException("Review ID is required");
            }
            Optional<Review> review = Optional.empty();
            try {
                review = reviewRepository.findById(UUID.fromString(id));
            } catch (Exception e) {
                throw new InternalServerException("Internal Server Error: Review Management Service is down...");
            }
            return review;
    }

    @Override
    public Review updateReview(Review review)   
            throws NotFoundException, InternalServerException, BadRequestException {
            Review review2 = new Review();
            if (review.getId() == null) {
                throw new BadRequestException("Review ID is required");
            }
            try {
                review2 = reviewRepository.save(review);
            } catch (Exception e) {
                throw new NotFoundException("Review not found with ID: " + review.getId());
            }
            return review2;
    }

    @Override
    public void deleteReview(String id)  
            throws NotFoundException, InternalServerException, BadRequestException {
            if (id == null) {
                throw new BadRequestException("Review ID is required");
            }
            try {
                reviewRepository.deleteById(UUID.fromString(id));
            } catch (Exception e) {
                throw new NotFoundException("Review not found with ID: " + id);
            }
    }

    public Review likeReview(UUID reviewId) throws NotFoundException, InternalServerException {
        Review review = new Review();
        try {
            review = updateReaction(reviewId, ReactionType.LIKE);
        } catch (Exception e) {
            throw new NotFoundException("Review not found with ID: " + reviewId);
        }
        return review;
    }
    
    public Review dislikeReview(UUID reviewId) throws NotFoundException, InternalServerException {
        Review review = new Review();
        try {
            review = updateReaction(reviewId, ReactionType.DISLIKE);
        } catch (Exception e) {
            throw new NotFoundException("Review not found with ID: " + reviewId);
        }
        return review;
    }

    public enum ReactionType {
        LIKE, DISLIKE
    }
    
    private Review updateReaction(UUID reviewId, ReactionType reactionType) throws NotFoundException, InternalServerException {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            switch (reactionType) {
                case LIKE:
                    review.incrementLikes();
                    break;
                case DISLIKE:
                    review.incrementDislikes();
                    break;
            }
            return reviewRepository.save(review);
        } else {
            throw new NotFoundException("Review not found with ID: " + reviewId);
        }
    }
}