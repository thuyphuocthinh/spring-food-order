package com.tpt.online_food_order.controller;

import com.tpt.online_food_order.dto.RestaurantDto;
import com.tpt.online_food_order.model.Restaurant;
import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.service.RestaurantService;
import com.tpt.online_food_order.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    private final UserService userService;

    public RestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(
                this.restaurantService.findRestaurantById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Restaurant>> searchRestaurantByKeyword(@PathVariable String keyword) throws Exception {
        return new ResponseEntity<>(
                this.restaurantService.searchRestaurant(keyword),
                HttpStatus.OK
        );
    }

    @GetMapping("")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<>(
                this.restaurantService.getAllRestaurants(),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}/user/toggle-favourite")
    public ResponseEntity<RestaurantDto> addRestaurantFavourite(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        String token = jwt.substring(7);
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(
                this.restaurantService.addFavouriteRestaurant(id, user),
                HttpStatus.OK
        );
    }
}
