package com.tpt.online_food_order.service;

import com.tpt.online_food_order.response.MessageResponse;

public interface EmailService {
    public MessageResponse sendEmail(String to, String subject, String body) throws Exception;
}
