package com.tpt.online_food_order.repository;

import com.tpt.online_food_order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByCustomerId(Long customerId);
    public List<Order> findByRestaurantId(Long restaurantId);
    public List<Order> findByRestaurantIdAndOrderStatus(Long restaurantId, String orderStatus);
}
