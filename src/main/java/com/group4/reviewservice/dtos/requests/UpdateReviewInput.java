package com.group4.reviewservice.dtos.requests;

import com.group4.reviewservice.enums.AttachmentTypeEnum;

import java.util.UUID;

// This UpdateReviewInput is a record which acts as a Data Transfer Object for the input request body of the update Review API route
public record UpdateReviewInput(
    UUID userId, 
    UUID serviceId, 
    String text, 
    AttachmentTypeEnum attachmentTypeEnum, 
    String attachmentUrl,
    int usefulCount,
    int funnyCount,
    int coolCount) {

}
