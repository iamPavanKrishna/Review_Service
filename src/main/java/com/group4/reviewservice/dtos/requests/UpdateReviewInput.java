package com.group4.reviewservice.dtos.requests;

import com.group4.reviewservice.enums.AttachmentTypeEnum;

import java.util.UUID;

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
