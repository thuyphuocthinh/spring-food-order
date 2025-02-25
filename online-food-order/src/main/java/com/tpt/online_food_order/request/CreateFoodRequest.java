package com.tpt.online_food_order.request;

import com.tpt.online_food_order.model.Category;
import com.tpt.online_food_order.model.IngredientItem;
import com.tpt.online_food_order.model.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private double price;
    private Category category;
    private List<String> images;
    private Restaurant restaurant;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientItem> ingredients;
}
