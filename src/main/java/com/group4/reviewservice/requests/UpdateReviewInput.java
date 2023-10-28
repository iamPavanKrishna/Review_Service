package com.group4.reviewservice.requests;

import com.group4.reviewservice.enums.AttacthmentTypeEnum;

import java.util.Date;

public record UpdateReviewInput(long userId, long serviceId, Integer stars, Date createdAt, Date updatedAt, String text, String useful, String funny, String cool, AttacthmentTypeEnum attacthmentTypeEnum, String attachmentUrl) {

}
