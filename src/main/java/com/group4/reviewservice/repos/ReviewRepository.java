package com.group4.reviewservice.repos;

import com.group4.reviewservice.models.Review;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// This a Data Access Layer for the Review Database
@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    
    List<Review> getAllReviewsByServiceId(UUID id);  // This method fetches all the reviews by the service id
    List<Review> getAllReviewsByUserId(UUID id);  // This method fetches all the reviews by the user id
}