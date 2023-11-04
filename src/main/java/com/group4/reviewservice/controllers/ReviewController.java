package com.group4.reviewservice.controllers;

import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.requests.CreateReviewInput;
import com.group4.reviewservice.requests.UpdateReviewInput;
import com.group4.reviewservice.services.ReviewService;

import jakarta.annotation.Generated;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    public ReviewService reviewService;
    
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // CreateReview
    @PostMapping("/r")
    public ResponseEntity<Review> createReview(@RequestBody CreateReviewInput createReviewInput){
        Review review = reviewService.create(createReviewInput.toReview());
        
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/r")
    public ResponseEntity<List<Review>> getAllReviews(){
        List<Review> reviews = reviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/r/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Integer id){
        Optional<Review> review = reviewService.findById(id);
        if (review.isPresent()){
            return new ResponseEntity<>(review.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable int id, @RequestBody UpdateReviewInput updateReviewInput) {
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
    
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        reviewService.delete(id);
    
        return ResponseEntity.noContent().build();
    }
}