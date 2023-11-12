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
        throws NotFoundException, TPAServiceException, InternalServerException, BadRequestException {
        try {
           reviewRepository.save(review);            
        } catch (Exception e) {
            throw new InternalServerException("Internal Server Error: Error Submitting Review");
        }    
        Review respReview = getReviewById(review.getId().toString()).get();
        // Sending Notification to the specified user
            NotificationRequest notificationRequesttoUser = new NotificationRequest(
                respReview.getServiceId().toString(),
                "Review is successfully submitted",
                "https://logowik.com/content/uploads/images/mail-notification4831.jpg",
                "email",
                "alert",
                "Review submitted"
            );
    
            // Sending Notification to the specified user
            NotificationRequest notificationRequest = new NotificationRequest(
                respReview.getServiceId().toString(),
                "Checkout the new review for your service that you have subscribed",
                "https://logowik.com/content/uploads/images/mail-notification4831.jpg",
                "email",
                "alert",
                "New Review received for your service"
            );
    
            String toUserUrl = baseNotificationUrl + "/" + respReview.getUserId().toString();

            try {
            notificationServiceCall.sendnotification(notificationRequesttoUser, toUserUrl);
            notificationServiceCall.sendnotification(notificationRequest, baseNotificationUrl);                      
            } catch (Exception e) {
                throw new TPAServiceException("Some error occured while sending notification");
            }
    
        return respReview;
    }


    // This is the implementation of the abstract method for submitting a review
    @Override
    public List<Review> getAllReviews()   
        throws NotFoundException, InternalServerException {
        try {
            List<Review> resp = reviewRepository.findAll();
            List<Review> reviews = new ArrayList<>();
            for (Review review : resp) {
                reviews.add(review);
            }
            return reviews;
        } catch (Exception e) {
            throw new InternalServerException("Internal Server Error: Unable to fetch reviews at the moment...");
        }

        
    }

    @Override
    public List<Review> getAllReviewsByServiceId(UUID id)
            throws BadRequestException {
        if (id == null || id.toString().isEmpty()) {
                throw new BadRequestException("Service ID is required");
            }
        List<Review> resp =  reviewRepository.getAllReviewsByServiceId(id);
        return resp;
        
    }

    @Override
    public List<Review> getAllReviewsByUserId(UUID id)    
        throws BadRequestException {
        if (id == null) {
            throw new BadRequestException("User ID is required");
        }
        List<Review> resp = reviewRepository.getAllReviewsByUserId(id);
        return resp;

    }

    @Override
    public Optional<Review> getReviewById(String id) 
        throws BadRequestException, InternalServerException {
        if (id == null || id.isEmpty()) {
            throw new BadRequestException("Review ID is required");
        }
        Optional<Review> reviewOptional;
        try {
            reviewOptional = reviewRepository.findById(UUID.fromString(id));
        } catch (Exception e) {
            throw new InternalServerException("Internal Server Error: Unable to fetch reviews at the moment...");
        }
        return reviewOptional;
    }

    @Override
    public Review updateReview(Review review)
            throws InternalServerException, BadRequestException, TPAServiceException {
        if (review.getId() == null || review.getId().toString().isEmpty()) {
            throw new BadRequestException("Review ID is required");
        }
        try {
            Review respReview = reviewRepository.save(review);
    
            // Sending Notification to the specified user
            NotificationRequest notificationRequest = new NotificationRequest(
                respReview.getServiceId().toString(),
                "Review is successfully updated",
                "https://logowik.com/content/uploads/images/mail-notification4831.jpg",
                "email",
                "alert",
                "Review updated"
            );
    
            String url = baseNotificationUrl + "/" + respReview.getUserId().toString();
    
            notificationServiceCall.sendnotification(notificationRequest, url);
    
            return respReview;
        } catch (Exception e) {
            throw new InternalServerException("Internal Server Error: Error Updating Review\n" + e.getMessage());
        }
    }

    @Override
    public String deleteReview(String id)
            throws BadRequestException, TPAServiceException, InternalServerException {
        if (id == null || id.isEmpty()) {
            throw new BadRequestException("Review ID is required");
        }
        Review respReview = getReviewById(id).get();
        reviewRepository.deleteById(UUID.fromString(id));
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
           throw new TPAServiceException("Some error occured while sending notification");
       }
       return "Review deleted successfully";
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
                    throw new TPAServiceException("Some error occured while sending notification");
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