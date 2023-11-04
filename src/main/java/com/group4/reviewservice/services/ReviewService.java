package com.group4.reviewservice.services;

import com.group4.reviewservice.models.Review;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    public Review create(Review review);

    public List<Review> findAll();

    public Optional<Review> findById(String id);

    public Review update(Review review);

    public void delete(String id);
}