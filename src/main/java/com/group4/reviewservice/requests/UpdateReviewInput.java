package com.group4.reviewservice.requests;

import com.group4.reviewservice.enums.AttacthmentTypeEnum;

import java.util.Date;
import java.util.UUID;

public record UpdateReviewInput(UUID userId, UUID serviceId, Integer stars, Date createdAt, Date updatedAt, String text, String useful, String funny, String cool, AttacthmentTypeEnum attacthmentTypeEnum, String attachmentUrl) {

}
