package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.Order;
import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest request, User user) throws Exception;
    public Order updateOrder(Long orderId, String orderStatus) throws Exception;
    public String cancelOrder(Long orderId) throws Exception;
    public List<Order> getOrdersOfUser(Long userId) throws Exception;
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;
}
