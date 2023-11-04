package com.group4.reviewservice.requests;


import org.springframework.scheduling.config.Task;
import java.util.Date;

import com.group4.reviewservice.enums.AttacthmentTypeEnum;
import com.group4.reviewservice.models.Review;

public record CreateReviewInput(long userId, long serviceId, Integer stars, Date createdAt, Date updatedAt, String text, String useful, String funny, String cool, AttacthmentTypeEnum attachmentTypeEnum, String attachmentUrl) {
    public Review toReview() {
        Review review = new Review();

        review.setUserId(userId)
        .setServiceId(serviceId)
        .setStars(stars)
        .setCreatedAt(createdAt)
        .setUpdatedAt(updatedAt)
        .setText(text)
        .setUseful(useful)
        .setFunny(funny)
        .setCool(cool)
        .setAttacthmentTypeEnum(attachmentTypeEnum)
        .setAttachmentUrl(attachmentUrl);
        

        return review;
    }
}
