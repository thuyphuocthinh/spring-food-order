package com.tpt.online_food_order.controller;

import com.tpt.online_food_order.model.Food;
import com.tpt.online_food_order.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Food>> search(@PathVariable("keyword") String keyword) {
        return new ResponseEntity<>(
                this.foodService.searchFood(keyword),
                HttpStatus.OK
        );
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>> getFoodByRestaurant(
            @PathVariable Long id,
            @RequestParam(name = "isVegetarian", required = false) Boolean isVegetarian,
            @RequestParam(name = "isNonvegetarian", required = false) Boolean isNonvegetarian,
            @RequestParam(name = "isSeasonal", required = false) Boolean isSeasonal,
            @RequestParam(name = "category", required = false) String category
    ) {
        return new ResponseEntity<>(
                this.foodService.getFoodByRestaurant(id, isVegetarian, isNonvegetarian, isSeasonal, category),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(
                this.foodService.findFoodById(id),
                HttpStatus.OK
        );
    }
}
