package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.Category;
import com.tpt.online_food_order.model.Restaurant;
import com.tpt.online_food_order.repository.CategoryRepository;
import com.tpt.online_food_order.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    private final RestaurantService restaurantService;

    private final RestaurantRepository restaurantRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, RestaurantService restaurantService, RestaurantRepository restaurantRepository) {
        this.categoryRepository = categoryRepository;
        this.restaurantService = restaurantService;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Category createCategory(String name, Long userId) {
        Restaurant restaurant = this.restaurantRepository.findByOwnerId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return this.categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> category = this.categoryRepository.findById(id);
        if(category.isPresent()) {
            return category.get();
        }
        throw new Exception("Category not found");
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception {
        return this.categoryRepository.findByRestaurantId(restaurantId);
    }
}
