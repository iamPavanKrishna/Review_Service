package com.group4.reviewservice.ThirdPartyServices;

import com.group4.reviewservice.dtos.responses.UserResponse;

public interface UserServiceCall {
     public UserResponse getUserDetails(String userId);
}
