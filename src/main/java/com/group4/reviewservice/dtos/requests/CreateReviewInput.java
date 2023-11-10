package com.group4.reviewservice.dtos.requests;

import java.util.UUID;

import com.group4.reviewservice.enums.AttachmentTypeEnum;
import com.group4.reviewservice.models.Review;

public record CreateReviewInput(
    UUID userId, 
    UUID serviceId, 
    String text, 
    AttachmentTypeEnum attachmentTypeEnum, 
    String attachmentUrl, 
    int usefulCount, 
    int funnyCount, 
    int coolCount) {
    public Review toReview() {

        return Review.builder()
        .userId(userId)
        .serviceId(serviceId)
        .text(text)
        .attachmentTypeEnum(attachmentTypeEnum)
        .attachmentUrl(attachmentUrl)
        .usefulCount(usefulCount)
        .funnyCount(funnyCount)
        .coolCount(coolCount)
        .build();
    }
}
