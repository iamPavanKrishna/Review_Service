package com.group4.reviewservice.controllers;

import com.group4.reviewservice.dtos.requests.CreateReviewInput;
import com.group4.reviewservice.dtos.requests.UpdateReviewInput;
import com.group4.reviewservice.enums.ReactionTypeEnum;
import com.group4.reviewservice.exceptions.*;
import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.services.ReviewServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    public ReviewServiceImpl reviewService;
  
    
    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    // API route to create a Review
    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody CreateReviewInput createReviewInput) 
            throws NotFoundException, TPAServiceException, InternalServerException {
        reviewService.submitReview(createReviewInput.toReview());
 
        return new ResponseEntity<>("Review Submitted", HttpStatus.CREATED);
    }

    // API route to fetch all Reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews()   
            throws NotFoundException, TPAServiceException, InternalServerException {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // API route to fetch all reviews of a particular service
    @GetMapping("/getreviewbyservice/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByServiceId(@PathVariable UUID id)   
            throws NotFoundException, BadRequestException {
        List<Review> reviews = reviewService.getAllReviewsByServiceId(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // API route to fetch all reviews of a particular user
    @GetMapping("/getreviewbyuser/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByUserId(@PathVariable UUID id)   
            throws NotFoundException, InternalServerException, BadRequestException {
        List<Review> reviews = reviewService.getAllReviewsByUserId(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // API route to fetch a Review by id
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable String id) 
            throws NotFoundException, InternalServerException, BadRequestException {
        Optional<Review> review = reviewService.getReviewById(id);
        if (review.isPresent()){
            return new ResponseEntity<>(review.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // API route to update a Review by id
    @PutMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody UpdateReviewInput updateReviewInput)   
            throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException {
        Optional<Review> review = reviewService.getReviewById(id);

        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Review reviewToUpdate = new Review();

        reviewToUpdate.setId(UUID.fromString(id));
        reviewToUpdate.setUserId(review.get().getUserId());
        reviewToUpdate.setServiceId(review.get().getServiceId());
        reviewToUpdate.setText(updateReviewInput.text());
        reviewToUpdate.setAttachmentTypeEnum(updateReviewInput.attachmentTypeEnum());
        reviewToUpdate.setAttachmentUrl(updateReviewInput.attachmentUrl());
        reviewToUpdate.setUsefulCount(review.get().getUsefulCount());
        reviewToUpdate.setFunnyCount(review.get().getFunnyCount());
        reviewToUpdate.setCoolCount(review.get().getCoolCount());

        Review reviewUpdated = reviewService.updateReview(reviewToUpdate);

        return new ResponseEntity<>(reviewUpdated, HttpStatus.OK);
    }

    // API route to delete a Review by id
    @DeleteMapping({"/delete/{id}"})
    public ResponseEntity<Void> deleteTask(@PathVariable String id)  
            throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException {
        reviewService.deleteReviewfromUserReaction(UUID.fromString(id));
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    // API route to add user specific reaction to the review
    @PostMapping("/react/{reviewId}/{reactionType}/{userId}")
    public ResponseEntity<Void> reactToReview(@PathVariable String reviewId, @PathVariable String reactionType, @PathVariable String userId) 
            throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException {
        reviewService.reactToReview(UUID.fromString(reviewId), ReactionTypeEnum.fromString(reactionType), UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }
}