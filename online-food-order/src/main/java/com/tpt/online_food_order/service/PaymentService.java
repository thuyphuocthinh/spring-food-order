package com.tpt.online_food_order.service;

import com.stripe.exception.StripeException;
import com.tpt.online_food_order.model.Order;
import com.tpt.online_food_order.response.PaymentResponse;

public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
