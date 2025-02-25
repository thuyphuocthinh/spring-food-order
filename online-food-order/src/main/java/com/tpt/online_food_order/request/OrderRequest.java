package com.tpt.online_food_order.request;

import com.tpt.online_food_order.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
