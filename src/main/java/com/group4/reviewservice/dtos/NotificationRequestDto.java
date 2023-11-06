package com.group4.reviewservice.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private String service_id;
    private String data;
    private String image_url;
    private String channel;  // [EMAIL, SMS].toLower()
    private String type;  // [PROMO, ALERT, INFO].toLower()
    private String subject;
}
