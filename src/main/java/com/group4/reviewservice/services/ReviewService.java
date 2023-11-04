package com.group4.reviewservice.services;

import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.repos.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> findAll() {
        List<Review> review = new ArrayList<>();
        reviewRepository.findAll().forEach(review::add);

        return review;
    }

    public Optional<Review> findById(UUID id) {
        return reviewRepository.findById(id);
    }

    public Review update(Review review) {
        return reviewRepository.save(review);
    }

    public void delete(UUID id) {
        reviewRepository.deleteById(id);
    }
}