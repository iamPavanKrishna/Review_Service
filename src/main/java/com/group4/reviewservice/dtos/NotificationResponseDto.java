package com.group4.reviewservice.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {
    private String message;
    private String service_id;
}
