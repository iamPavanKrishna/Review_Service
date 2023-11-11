package com.group4.reviewservice.dtos.requests;

import java.util.UUID;

import com.group4.reviewservice.enums.AttachmentTypeEnum;
import com.group4.reviewservice.models.Review;

// This CreateReviewinput is a record which acts as a Data Transfer Object for the input request body for the submit Review API route
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
        /*
            This method converts a record into a review and returns it.
        */

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
