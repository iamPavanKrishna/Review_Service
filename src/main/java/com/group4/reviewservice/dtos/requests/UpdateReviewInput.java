package com.group4.reviewservice.dtos.requests;

import com.group4.reviewservice.enums.AttachmentTypeEnum;


// This UpdateReviewInput is a record which acts as a Data Transfer Object for the input request body of the update Review API route
public record UpdateReviewInput(
    String text, 
    AttachmentTypeEnum attachmentTypeEnum, 
    String attachmentUrl) {

}
