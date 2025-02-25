package com.tpt.online_food_order.controller;

import com.tpt.online_food_order.model.Restaurant;
import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.request.CreateRestaurantRequest;
import com.tpt.online_food_order.response.MessageResponse;
import com.tpt.online_food_order.service.RestaurantService;
import com.tpt.online_food_order.service.UserService;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/restaurants")
public class AdminRestaurantController {
    private final RestaurantService restaurantService;

    private final UserService userService;

    public AdminRestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest createRestaurantRequest,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        String token = jwt.substring(7);
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(this.restaurantService.createRestaurant(createRestaurantRequest, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<>(this.restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest createRestaurantRequest,
            @PathVariable Long id
    ) throws Exception {
        return new ResponseEntity<>(
                this.restaurantService.updateRestaurant(id, createRestaurantRequest),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @PathVariable Long id
    ) throws Exception {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(this.restaurantService.deleteRestaurant(id));
        return new ResponseEntity<>(
                messageResponse,
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @PathVariable Long id
    ) throws Exception {
        return new ResponseEntity<>(
                this.restaurantService.updateRestaurantStatus(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        String token = jwt.substring(7);
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(
                this.restaurantService.getRestaurantByUserId(user.getId()),
                HttpStatus.OK
        );
    }
}
