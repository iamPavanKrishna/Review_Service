package com.group4.reviewservice.requests;

import java.util.UUID;

import com.group4.reviewservice.enums.AttacthmentTypeEnum;
import com.group4.reviewservice.models.Review;

public record CreateReviewInput(UUID userId, UUID serviceId, String text, Long likes, Long dislikes, AttacthmentTypeEnum attachmentTypeEnum, String attachmentUrl) {
    public Review toReview() {

        return Review.builder()
        .userId(userId)
        .serviceId(serviceId)
        .text(text)
        .likes(likes)
        .dislikes(dislikes)
        .attacthmentTypeEnum(attachmentTypeEnum)
        .attachmentUrl(attachmentUrl)
        .build();
    }
}
