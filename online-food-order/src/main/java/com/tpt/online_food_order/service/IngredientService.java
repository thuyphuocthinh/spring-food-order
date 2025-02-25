package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.IngredientCategory;
import com.tpt.online_food_order.model.IngredientItem;

import java.util.List;

public interface IngredientService {
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;
    public List<IngredientCategory> findAllIngredientCategories();
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception;
    public IngredientItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;
    public List<IngredientItem> findIngredientItemsByRestaurant(Long restaurantId) throws Exception;
    public IngredientItem updateStock(Long id) throws Exception;
}
