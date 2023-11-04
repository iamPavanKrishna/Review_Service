package com.group4.reviewservice.services;

import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.repos.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("ReviewService")
@Primary
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> findAll() {
        List<Review> review = new ArrayList<>();
        reviewRepository.findAll().forEach(review::add);

        return review;
    }

    @Override
    public List<Review> findAllByServiceId(UUID id){
        return  reviewRepository.findAllByServiceId((id));

    }

    @Override
    public Optional<Review> findById(String id) {
        return reviewRepository.findById(UUID.fromString(id));
    }

    @Override
    public Review update(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void delete(String id) {
        reviewRepository.deleteById(UUID.fromString(id));
    }
}