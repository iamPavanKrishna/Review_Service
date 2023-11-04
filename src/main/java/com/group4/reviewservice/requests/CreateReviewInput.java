package com.group4.reviewservice.requests;


import java.util.Date;
import java.util.UUID;

import com.group4.reviewservice.enums.AttacthmentTypeEnum;
import com.group4.reviewservice.models.Review;

public record CreateReviewInput(UUID userId, UUID serviceId, Integer stars, String text, String useful, String funny, String cool, AttacthmentTypeEnum attachmentTypeEnum, String attachmentUrl) {
    public Review toReview() {
        Review review = new Review();

        review.setUserId(userId)
        .setServiceId(serviceId)
        .setStars(stars)
        .setText(text)
        .setUseful(useful)
        .setFunny(funny)
        .setCool(cool)
        .setAttacthmentTypeEnum(attachmentTypeEnum)
        .setAttachmentUrl(attachmentUrl);
        

        return review;
    }
}
