package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.IngredientCategory;
import com.tpt.online_food_order.model.IngredientItem;
import com.tpt.online_food_order.model.Restaurant;
import com.tpt.online_food_order.repository.IngredientCategoryRepository;
import com.tpt.online_food_order.repository.IngredientItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientItemRepository ingredientItemRepository;

    private final IngredientCategoryRepository ingredientCategoryRepository;

    private final RestaurantService restaurantService;

    public IngredientServiceImpl(IngredientItemRepository ingredientItemRepository, IngredientCategoryRepository ingredientCategoryRepository, RestaurantService restaurantService) {
        this.ingredientItemRepository = ingredientItemRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = this.restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = this.ingredientCategoryRepository.findById(id);
        if(ingredientCategory.isPresent()) {
            return ingredientCategory.get();
        }
        throw new Exception("Ingredient category not found");
    }

    @Override
    public List<IngredientCategory> findAllIngredientCategories() {
        return this.ingredientCategoryRepository.findAll();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = this.restaurantService.findRestaurantById(restaurantId);
        if(restaurant == null) {
            throw new Exception("Restaurant not found");
        }
        return this.ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<IngredientItem> findIngredientItemsByRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = this.restaurantService.findRestaurantById(restaurantId);
        if(restaurant == null) {
            throw new Exception("Restaurant not found");
        }
        return this.ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = this.restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(categoryId);
        IngredientItem ingredientItem = new IngredientItem();
        ingredientItem.setName(ingredientName);
        ingredientItem.setRestaurant(restaurant);
        ingredientItem.setCategory(ingredientCategory);
        return this.ingredientItemRepository.save(ingredientItem);
    }

    @Override
    public IngredientItem updateStock(Long id) throws Exception {
        Optional<IngredientItem> ingredientItem = this.ingredientItemRepository.findById(id);

        if(ingredientItem.isPresent()) {
            IngredientItem ingredientItem1 = ingredientItem.get();
            ingredientItem1.setInStock(!ingredientItem1.isInStock());
            return this.ingredientItemRepository.save(ingredientItem1);
        }

        throw new Exception("Ingredient item not found");
    }
}
