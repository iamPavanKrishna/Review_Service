package com.group4.reviewservice.requests;

import com.group4.reviewservice.enums.AttacthmentTypeEnum;

import java.util.UUID;

public record UpdateReviewInput(UUID userId, UUID serviceId, String text, Long likes, Long dislikes, AttacthmentTypeEnum attacthmentTypeEnum, String attachmentUrl) {

}
