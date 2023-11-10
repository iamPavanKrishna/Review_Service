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

    // CreateReview
    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody CreateReviewInput createReviewInput) 
            throws NotFoundException, TPAServiceException, InternalServerException {
        reviewService.submitReview(createReviewInput.toReview());
 
        return new ResponseEntity<>("Review Submitted", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews()   
            throws NotFoundException, TPAServiceException, InternalServerException {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/getreviewbyservice/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByServiceId(@PathVariable UUID id)   
            throws NotFoundException, InternalServerException, BadRequestException {
        List<Review> reviews = reviewService.getAllReviewsByServiceId(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/getreviewbyuser/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByUserId(@PathVariable UUID id)   
            throws NotFoundException, InternalServerException, BadRequestException {
        List<Review> reviews = reviewService.getAllReviewsByUserId(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable String id) 
            throws NotFoundException, InternalServerException, BadRequestException {
        Optional<Review> review = reviewService.getReviewById(id);
        if (review.isPresent()){
            return new ResponseEntity<>(review.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody UpdateReviewInput updateReviewInput)   
            throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException {
        Optional<Review> review = reviewService.getReviewById(id);

        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Review reviewToUpdate = new Review();

        reviewToUpdate.setId(UUID.fromString(id));
        reviewToUpdate.setUserId(updateReviewInput.userId());
        reviewToUpdate.setServiceId(updateReviewInput.serviceId());
        reviewToUpdate.setText(updateReviewInput.text());
        reviewToUpdate.setAttachmentTypeEnum(updateReviewInput.attachmentTypeEnum());
        reviewToUpdate.setAttachmentUrl(updateReviewInput.attachmentUrl());

        Review reviewUpdated = reviewService.updateReview(reviewToUpdate);

        return new ResponseEntity<>(reviewUpdated, HttpStatus.OK);
    }
    
    @DeleteMapping({"/delete/{id}"})
    public ResponseEntity<Void> deleteTask(@PathVariable String id)  
            throws NotFoundException, InternalServerException, BadRequestException, TPAServiceException {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/react/{reviewId}/{reactionType}/{userId}")
    public ResponseEntity<Void> reactToReview(@PathVariable String reviewId, @PathVariable String reactionType, @PathVariable String userId) 
            throws NotFoundException, InternalServerException, BadRequestException {
        reviewService.reactToReview(UUID.fromString(reviewId), ReactionTypeEnum.fromString(reactionType), UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }
}