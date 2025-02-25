package com.tpt.online_food_order.controller;

import com.tpt.online_food_order.model.Food;
import com.tpt.online_food_order.model.Restaurant;
import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.request.CreateFoodRequest;
import com.tpt.online_food_order.response.MessageResponse;
import com.tpt.online_food_order.service.FoodService;
import com.tpt.online_food_order.service.RestaurantService;
import com.tpt.online_food_order.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/food")
public class AdminFoodController {
    private final FoodService foodService;

    private final UserService userService;

    private final RestaurantService restaurantService;

    public AdminFoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
        this.foodService = foodService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<Food> createFood(
            @RequestBody CreateFoodRequest request,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = this.userService.findUserByJwtToken(jwt.substring(7));
        Restaurant restaurant = this.restaurantService.findRestaurantById(request.getRestaurant().getId());
        Food food = this.foodService.createFood(
                request,
                request.getCategory(),
                restaurant
        );
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable long id) throws Exception {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(this.foodService.deleteFood(id));
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<Food> updateAvailability(
            @PathVariable Long id
    ) throws Exception {
        return new ResponseEntity<>(
                this.foodService.updateAvailabilityStatus(id),
                HttpStatus.OK
        );
    }
}
