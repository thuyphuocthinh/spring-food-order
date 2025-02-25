package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.Category;
import com.tpt.online_food_order.model.Food;
import com.tpt.online_food_order.model.Restaurant;
import com.tpt.online_food_order.repository.FoodRepository;
import com.tpt.online_food_order.repository.RestaurantRepository;
import com.tpt.online_food_order.request.CreateFoodRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    private final RestaurantRepository restaurantRepository;

    public FoodServiceImpl(FoodRepository foodRepository, RestaurantRepository restaurantRepository) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setIngredients(request.getIngredients());
        food.setImages(request.getImages());
        food.setName(request.getName());
        food.setSeasonal(request.isSeasonal());
        food.setVegetarian(request.isVegetarian());

        restaurant.getFoods().add(food);
        this.restaurantRepository.save(restaurant);

        return this.foodRepository.save(food);
    }

    @Override
    public String deleteFood(Long foodId) throws Exception {
        Food food = this.foodRepository.findFoodById(foodId);
        food.setRestaurant(null);
        this.foodRepository.delete(food);
        return "Success";
    }

    @Override
    public List<Food> getFoodByRestaurant(Long restaurantId, boolean isVegeterian, boolean isNonvegeterian, boolean isSeasonal, String foodCategory) {
        List<Food> foods = this.foodRepository.findByRestaurantId(restaurantId);

        if(isVegeterian) {
            foods = filterByVegeterian(foods, isVegeterian);
        }

        if(isNonvegeterian) {
            foods = filterByNonvegetarian(foods, isNonvegeterian);
        }

        if(isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }

        if(foodCategory != null && !foodCategory.isEmpty()) {
            foods = filterByCategory(foods, foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getCategory() != null) {
                return food.getCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonvegetarian(List<Food> foods, boolean isNonvegeterian) {
        return foods.stream().filter(food -> food.isVegetarian() == isNonvegeterian).collect(Collectors.toList());
    }

    private List<Food> filterByVegeterian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return this.foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long id) throws Exception {
        Optional<Food> food = this.foodRepository.findById(id);
        if(food.isEmpty()) {
            throw new Exception("Food not found");
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = this.foodRepository.findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return this.foodRepository.save(food);
    }
}
