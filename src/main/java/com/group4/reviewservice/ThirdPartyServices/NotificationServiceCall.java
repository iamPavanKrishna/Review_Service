package com.group4.reviewservice.ThirdPartyServices;

import com.group4.reviewservice.dtos.requests.NotificationRequest;
import com.group4.reviewservice.dtos.responses.NotificationResponse;

public interface NotificationServiceCall {
     public NotificationResponse sendnotification(NotificationRequest notificationRequest, String url);
}

