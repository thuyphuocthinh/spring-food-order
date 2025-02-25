package com.tpt.online_food_order.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
