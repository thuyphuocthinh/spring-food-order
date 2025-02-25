package com.tpt.online_food_order.controller;

import com.tpt.online_food_order.model.Category;
import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.service.CategoryService;
import com.tpt.online_food_order.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CategoryController {
    private final CategoryService categoryService;

    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping("admin/category")
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = this.userService.findUserByJwtToken(jwt.substring(7));
        return new ResponseEntity<>(
                this.categoryService.createCategory(category.getName(), user.getId()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<Category>> getCategoriesByRestaurant(
            @PathVariable Long id
    ) throws Exception {
        return new ResponseEntity<>(
                this.categoryService.findCategoryByRestaurantId(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/admin/category/{id}")
    public ResponseEntity<Category> getCategoryById(
            @PathVariable Long id
    ) throws Exception {
        return new ResponseEntity<>(
                this.categoryService.findCategoryById(id),
                HttpStatus.OK
        );
    }
}
