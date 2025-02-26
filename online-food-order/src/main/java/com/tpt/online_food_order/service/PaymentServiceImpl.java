package com.tpt.online_food_order.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tpt.online_food_order.model.Order;
import com.tpt.online_food_order.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Override
    public PaymentResponse createPaymentLink(Order order) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/order/" + order.getId() + "/payment/success")
                .setCancelUrl("http://localhost:3000/order/" + order.getId() + "/payment/failed")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("USD")
                                .setUnitAmount((long) order.getTotalPrice())
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("TPT")
                                                .build()
                                )
                                .build()
                        )
                        .build()
                )
                .build();

        Session session = Session.create(params);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentUrl(session.getUrl());

        return paymentResponse;
    }
}
