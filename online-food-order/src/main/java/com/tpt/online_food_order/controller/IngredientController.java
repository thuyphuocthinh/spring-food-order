package com.tpt.online_food_order.controller;

import com.tpt.online_food_order.model.IngredientCategory;
import com.tpt.online_food_order.model.IngredientItem;
import com.tpt.online_food_order.request.IngredientCategoryRequest;
import com.tpt.online_food_order.request.IngredientItemRequest;
import com.tpt.online_food_order.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/admin/ingredient/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest request) throws Exception {
        return new ResponseEntity<>(
                this.ingredientService.createIngredientCategory(
                        request.getName(),
                        request.getRestaurantId()
                ),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/admin/ingredient/item")
    public ResponseEntity<IngredientItem> createIngredientItem(
            @RequestBody IngredientItemRequest request
            ) throws Exception {
        return new ResponseEntity<>(
                this.ingredientService.createIngredientItem(
                        request.getRestaurantId(),
                        request.getIngredientName(),
                        request.getCategoryId()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/admin/ingredient/item/{id}/stock")
    public ResponseEntity<IngredientItem> updateIngredientItem(
            @PathVariable Long id
    ) throws Exception {
        return new ResponseEntity<>(
                this.ingredientService.updateStock(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/ingredient/item/restaurant/{id}")
    public ResponseEntity<List<IngredientItem>> getListItemsByRestaurant(
            @PathVariable Long id
    ) throws Exception {
        return new ResponseEntity<>(
                this.ingredientService.findIngredientItemsByRestaurant(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/ingredient/category/restaurant/{id}")
    public ResponseEntity<List<IngredientCategory>> getListCategoriesByRestaurant(
            @PathVariable Long id
    ) throws Exception {
        return new ResponseEntity<>(
                this.ingredientService.findIngredientCategoryByRestaurantId(id),
                HttpStatus.OK
        );
    }
}
