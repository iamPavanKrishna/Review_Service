package com.group4.reviewservice.controllers;

import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.requests.CreateReviewInput;
import com.group4.reviewservice.requests.UpdateReviewInput;
import com.group4.reviewservice.services.ReviewService;

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
    public ReviewService reviewService;
  
    
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // CreateReview
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody CreateReviewInput createReviewInput){
        Review review = reviewService.create(createReviewInput.toReview());
        
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(){
        List<Review> reviews = reviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/getservicereview/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByServiceId(@PathVariable UUID id){
        List<Review> reviews = reviewService.findAllByServiceId(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable String id){
        Optional<Review> review = reviewService.findById(id);
        if (review.isPresent()){
            return new ResponseEntity<>(review.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody UpdateReviewInput updateReviewInput) {
        Optional<Review> review = reviewService.findById(id);

        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Review reviewToUpdate = review.get();

        reviewToUpdate.setUserId(updateReviewInput.userId());
        reviewToUpdate.setServiceId(updateReviewInput.serviceId());
        reviewToUpdate.setStars(updateReviewInput.stars());
        reviewToUpdate.setCreatedAt(updateReviewInput.createdAt());
        reviewToUpdate.setUpdatedAt(updateReviewInput.updatedAt());
        reviewToUpdate.setText(updateReviewInput.text());
        reviewToUpdate.setUseful(updateReviewInput.useful());
        reviewToUpdate.setFunny(updateReviewInput.funny());
        reviewToUpdate.setCool(updateReviewInput.cool());
        reviewToUpdate.setAttacthmentTypeEnum(updateReviewInput.attacthmentTypeEnum());
        reviewToUpdate.setAttachmentUrl(updateReviewInput.attachmentUrl());

        Review reviewUpdated = reviewService.update(reviewToUpdate);

        return new ResponseEntity<>(reviewUpdated, HttpStatus.OK);
    }
    
    @DeleteMapping({"/delete/{id}"})
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        reviewService.delete(id);
    
        return ResponseEntity.noContent().build();
    }
}