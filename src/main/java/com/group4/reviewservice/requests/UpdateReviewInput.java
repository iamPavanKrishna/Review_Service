package com.group4.reviewservice.requests;

import com.group4.reviewservice.enums.AttacthmentTypeEnum;

import java.util.UUID;

public record UpdateReviewInput(UUID userId, UUID serviceId, String text, String useful, String funny, String cool, AttacthmentTypeEnum attacthmentTypeEnum, String attachmentUrl) {

}
