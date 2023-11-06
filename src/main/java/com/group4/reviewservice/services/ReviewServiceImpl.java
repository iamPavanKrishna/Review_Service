package com.group4.reviewservice.services;

import com.group4.reviewservice.dtos.NotificationRequestDto;
import com.group4.reviewservice.dtos.NotificationResponseDto;
import com.group4.reviewservice.exceptions.*;
import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.repos.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.group4.reviewservice.thirdPartyNotification.TPAServiceCall;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("ReviewService")
@Primary
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final TPAServiceCall tpaServiceCall;


    @Override
    public Review submitReview(Review review) 
            throws NotFoundException, TPAServiceException, InternalServerException, BadRequestException {
                Review review2 = reviewRepository.save(review);
                NotificationRequestDto notificationRequestDto = new NotificationRequestDto();

                notificationRequestDto.setService_id(review2.getServiceId().toString());
                notificationRequestDto.setData("Successfully Integrated Review Service with Notification Service");
                notificationRequestDto.setImage_url("https://logowik.com/content/uploads/images/mail-notification4831.jpg");
                notificationRequestDto.setSubject("Review Service and Notification Service Integration");
                notificationRequestDto.setType("alert");
                notificationRequestDto.setChannel("email");

                NotificationResponseDto notificationResponseDto = tpaServiceCall.sendnotification(notificationRequestDto);
                System.out.println(notificationResponseDto);
                return review2;
    }

    @Override
    public List<Review> getAllReviews()   
            throws NotFoundException, TPAServiceException, InternalServerException {
        List<Review> review = new ArrayList<>();
        reviewRepository.findAll().forEach(review::add);

        return review;
    }

    @Override
    public List<Review> getAllReviewsByServiceId(UUID id)    
    throws NotFoundException, TPAServiceException, InternalServerException{
        return  reviewRepository.getAllReviewsByServiceId(id);

    }

    @Override
    public List<Review> getAllReviewsByUserId(UUID id)    
    throws NotFoundException, TPAServiceException, InternalServerException{
        return  reviewRepository.getAllReviewsByUserId(id);

    }

    @Override
    public Optional<Review> getReviewById(String id) 
            throws NotFoundException, InternalServerException {
        return reviewRepository.findById(UUID.fromString(id));
    }

    @Override
    public Review updateReview(Review review)   
            throws NotFoundException, InternalServerException, BadRequestException {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(String id)  
            throws NotFoundException, InternalServerException {
        reviewRepository.deleteById(UUID.fromString(id));
    }
}