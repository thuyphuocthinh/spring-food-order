package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.Category;
import com.tpt.online_food_order.model.Food;
import com.tpt.online_food_order.model.Restaurant;
import com.tpt.online_food_order.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);
    String deleteFood(Long foodId) throws Exception;
    public List<Food> getFoodByRestaurant(Long restaurantId, boolean isVegeterian, boolean isNonvegeterian, boolean isSeasonal, String foodCategory);
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long id) throws Exception;
    // public Food updateFood(CreateFoodRequest request, Long id) throws Exception;
    public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
