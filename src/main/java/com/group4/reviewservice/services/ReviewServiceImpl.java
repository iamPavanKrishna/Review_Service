package com.group4.reviewservice.services;

import com.group4.reviewservice.ThirdPartyServices.NotificationServiceCallImpl;
import com.group4.reviewservice.ThirdPartyServices.UserServiceCallImpl;
import com.group4.reviewservice.dtos.requests.NotificationRequest;
import com.group4.reviewservice.dtos.responses.UserResponse;
import com.group4.reviewservice.enums.ReactionTypeEnum;
import com.group4.reviewservice.exceptions.*;
import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.models.UserReaction;
import com.group4.reviewservice.repos.ReviewRepository;
import com.group4.reviewservice.repos.UserReactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// This is the Implementation of the Service Calls
@Service("ReviewService")
@Primary
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final UserReactionRepository userReactionRepository;
    private final NotificationServiceCallImpl notificationServiceCall;
    private final UserServiceCallImpl userServiceCall;
    
    @Value("${NOTIFICATIONSERVICE_URL}")
    private String baseNotificationUrl;
    


    // This is the implementation of the abstract method for submitting a review
    @Override
    public Review submitReview(Review review) 
        throws NotFoundException, TPAServiceException, InternalServerException {
        Review respReview = new Review();

        try {
            respReview = reviewRepository.save(review);
        } catch (Exception e) {
            throw new InternalServerException("Internal Server Error: Error Submitting Review");
        }
        // Sending Notification to the specified user
        NotificationRequest notificationRequesttoUser = new NotificationRequest(
                respReview.getServiceId().toString(),
                "Review is successfully submitted",
                "https://logowik.com/content/uploads/images/mail-notification4831.jpg",
                "email",
                "alert",
                "Review submitted");
        // Sending Notification to the specified user
        NotificationRequest notificationRequest = new NotificationRequest(
                respReview.getServiceId().toString(),
                "Checkout the new review for your service that you have subscribed",
                "https://logowik.com/content/uploads/images/mail-notification4831.jpg",
                "email",
                "alert",
                "New Review received for your service");
        try {

            String toUserUrl = baseNotificationUrl + "/" + respReview.getUserId().toString();

            notificationServiceCall.sendnotification(notificationRequesttoUser, toUserUrl);
            notificationServiceCall.sendnotification(notificationRequest, baseNotificationUrl);
        } catch (Exception e) {
            throw new TPAServiceException(e.getMessage());
        }
        return respReview;            
    }


    // This is the implementation of the abstract method for submitting a review
    @Override
    public List<Review> getAllReviews()   
        throws NotFoundException, InternalServerException {
        List<Review> review = new ArrayList<>();
        try {
            reviewRepository.findAll().forEach(review::add);
        } catch (Exception e) {
            throw new NotFoundException("No reviews not found!");
        }

        return review;
    }

    @Override
    public List<Review> getAllReviewsByServiceId(UUID id)
        throws NotFoundException, BadRequestException {
        if (id == null) {
                throw new BadRequestException("Service ID is required");
            }
        try{
            return reviewRepository.getAllReviewsByServiceId(id);
        } catch (Exception e) {
            throw new NotFoundException("No Review is found with serviceId: "+id);
        }
    }

    @Override
    public List<Review> getAllReviewsByUserId(UUID id)    
        throws NotFoundException, InternalServerException, BadRequestException {
        if (id == null) {
            throw new BadRequestException("User ID is required");
        }
        try{
            return reviewRepository.getAllReviewsByUserId(id);
        } catch (Exception e) {
            throw new InternalServerException("Internal Server Error: Unable to fetch reviews at the moment...");
        }

    }

    @Override
    public Optional<Review> getReviewById(String id) 
        throws NotFoundException, InternalServerException, BadRequestException {
        if (id == null || id.isEmpty()) {
            throw new BadRequestException("Review ID is required");
        }
        try {
            Optional<Review> reviewOptional = reviewRepository.findById(UUID.fromString(id));
            if (!reviewOptional.isPresent()) {
                throw new NotFoundException("Review not found with ID: " + id);
            }
            return reviewOptional;
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public Review updateReview(Review review)   
        throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException {
        if (review.getId() == null || review.getId().toString().isEmpty()) {
            throw new BadRequestException("Review ID is required");
        }
            Review respReview = new Review();

        try {
            respReview = reviewRepository.save(review);

        } catch (Exception e) {
            throw new InternalServerException("Internal Server Error: Error Updating Review" + e.getMessage());
        }
        NotificationRequest notificationRequest = new NotificationRequest(
                respReview.getServiceId().toString(),
                "Review is successfully updated",
                "https://logowik.com/content/uploads/images/mail-notification4831.jpg",
                "email",
                "alert",
                "Review updated");
        try {

            String url = baseNotificationUrl + "/" + respReview.getUserId().toString();

            notificationServiceCall.sendnotification(notificationRequest, url);
        } catch (Exception e) {
            throw new TPAServiceException("Notification Service is down");
        }
        
        return respReview;
    }

    @Override
    public void deleteReview(String id)
            throws NotFoundException, BadRequestException, TPAServiceException, InternalServerException {
        Review respReview = getReviewById(id).orElseThrow(() -> new NotFoundException("Review not found with ID: " + id));
        if (id == null || id.isEmpty()) {
            throw new BadRequestException("Review ID is required");
        }
        try {
            reviewRepository.deleteById(UUID.fromString(id));
        } catch (Exception e) {
            throw new NotFoundException("Review not found with ID: " + id);
        }
        NotificationRequest notificationRequest = new NotificationRequest(
            respReview.getServiceId().toString(), 
            "Review is successfully deleted", 
            "https://logowik.com/content/uploads/images/mail-notification4831.jpg", 
            "email",
            "alert", 
            "Review deleted");
        try {
            String url = baseNotificationUrl + "/" +  respReview.getUserId().toString();
            notificationServiceCall.sendnotification(notificationRequest, url);
       } catch (Exception e) {
           throw new TPAServiceException("Notification Service is down");
       }
    }

    public void reactToReview(UUID reviewId, ReactionTypeEnum reactionType, UUID userId)
        throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException {
        if (reviewId == null || reviewId.toString().isEmpty()) {
            throw new BadRequestException("Review ID is required");
        }
        if (userId == null || userId.toString().isEmpty()) {
            throw new BadRequestException("User ID is required");
        }
        Optional<Review> optionalReview = getReviewById(reviewId.toString());
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();

            Optional<UserReaction> existingReaction = userReactionRepository.findByReviewAndUserIdAndReactionType(review, userId, reactionType);

            if (existingReaction.isPresent()) {
                // User is undoing their previous reaction
                userReactionRepository.delete(existingReaction.get());
                decrementReactionCount(review, reactionType);
            } else {
                // User is reacting for the first time
                UserReaction userReaction = new UserReaction();
                userReaction.setReview(review);
                userReaction.setUserId(userId);
                userReaction.setReactionType(reactionType);
                userReactionRepository.save(userReaction);

                incrementReactionCount(review, reactionType);
                String data = fetchuserdetails(userId) + " reacted to your review";

                NotificationRequest notificationRequest = new NotificationRequest(
                    review.getServiceId().toString(), 
                    data, 
                    "https://logowik.com/content/uploads/images/mail-notification4831.jpg", 
                    "email",
                    "alert", 
                    "New Reaction received for your review");
                try {
                    String url = baseNotificationUrl + "/" +  review.getUserId().toString();
                    notificationServiceCall.sendnotification(notificationRequest, url);
                } catch (Exception e) {
                    throw new TPAServiceException(e.getMessage());
                }
            }
        }
    }

    public String fetchuserdetails(UUID userId) throws TPAServiceException {
        UserResponse response = userServiceCall.getUserDetails(userId.toString());

        return response.name().toString() + "(" + response.email().toString() + ")";

    }

    public void incrementReactionCount(Review review, ReactionTypeEnum reactionType) {
        switch (reactionType) {
            case USEFUL:
                review.incrementUseful();
                break;
            case FUNNY:
                review.incrementFunny();
                break;
            case COOL:
                review.incrementCool();
                break;
        }
        reviewRepository.save(review);
    }

    public void decrementReactionCount(Review review, ReactionTypeEnum reactionType) {
        switch (reactionType) {
            case USEFUL:
                review.decrementUseful();
                break;
            case FUNNY:
                review.decrementFunny();
                break;
            case COOL:
                review.decrementCool();
                break;
        }
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReviewfromUserReaction(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            userReactionRepository.deleteByReview(review);
        }
    }
}