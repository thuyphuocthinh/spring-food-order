package com.tpt.online_food_order.service;

import com.tpt.online_food_order.dto.RestaurantDto;
import com.tpt.online_food_order.model.Restaurant;
import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user);
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurantRequest) throws Exception;
    public String deleteRestaurant(Long restaurantId) throws Exception;
    public Restaurant findRestaurantById(Long restaurantId) throws Exception;
    public List<Restaurant> getAllRestaurants();
    public List<Restaurant> searchRestaurant(String keyword);
    public Restaurant getRestaurantByUserId(Long userId) throws Exception;
    public RestaurantDto addFavouriteRestaurant(Long restaurantId, User user) throws Exception;
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
