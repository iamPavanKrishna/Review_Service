package com.group4.reviewservice.requests;

import java.util.UUID;

import com.group4.reviewservice.enums.AttacthmentTypeEnum;
import com.group4.reviewservice.models.Review;

public record CreateReviewInput(UUID userId, UUID serviceId, String text, String useful, String funny, String cool, AttacthmentTypeEnum attachmentTypeEnum, String attachmentUrl) {
    public Review toReview() {
        Review review = new Review();

        review.setUserId(userId)
        .setServiceId(serviceId)
        .setText(text)
        .setUseful(useful)
        .setFunny(funny)
        .setCool(cool)
        .setAttacthmentTypeEnum(attachmentTypeEnum)
        .setAttachmentUrl(attachmentUrl);
        

        return review;
    }
}
