package com.tpt.online_food_order.repository;

import com.tpt.online_food_order.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findByRestaurantId(Long restaurantId);
}
