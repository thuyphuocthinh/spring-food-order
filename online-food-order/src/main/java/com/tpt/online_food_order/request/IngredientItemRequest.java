package com.tpt.online_food_order.request;

import lombok.Data;

@Data
public class IngredientItemRequest {
    private String ingredientName;
    private Long categoryId;
    private Long restaurantId;
}
