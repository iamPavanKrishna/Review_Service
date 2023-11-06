package com.group4.reviewservice.repos;

import com.group4.reviewservice.models.Review;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> getAllReviewsByServiceId(UUID id);
    List<Review> getAllReviewsByUserId(UUID id);
}