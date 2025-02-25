package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name, Long userId);
    public Category findCategoryById(Long id) throws Exception;
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;
}
