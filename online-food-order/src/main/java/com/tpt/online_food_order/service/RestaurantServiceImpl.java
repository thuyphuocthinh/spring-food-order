package com.tpt.online_food_order.service;

import com.tpt.online_food_order.dto.RestaurantDto;
import com.tpt.online_food_order.model.Address;
import com.tpt.online_food_order.model.Restaurant;
import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.repository.AddressRepository;
import com.tpt.online_food_order.repository.RestaurantRepository;
import com.tpt.online_food_order.repository.UserRepository;
import com.tpt.online_food_order.request.CreateRestaurantRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    private final RestaurantRepository restaurantRepository;

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user) {
        Address address = addressRepository.save(createRestaurantRequest.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(createRestaurantRequest.getName());
        restaurant.setContactInformation(createRestaurantRequest.getContactInformation());
        restaurant.setCuisineType(createRestaurantRequest.getCuisineType());
        restaurant.setDescription(createRestaurantRequest.getDescription());
        restaurant.setImages(createRestaurantRequest.getImages());
        restaurant.setOpeningHours(createRestaurantRequest.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOpen(true);
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurantRequest) throws Exception {
        Restaurant restaurant = this.restaurantRepository.findRestaurantById(restaurantId);

        if(restaurant.getCuisineType() != null) {
            restaurant.setName(updatedRestaurantRequest.getCuisineType());
        }
        if(restaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurantRequest.getDescription());
        }
        if(restaurant.getName() != null) {
            restaurant.setName(updatedRestaurantRequest.getName());
        }

        return this.restaurantRepository.save(restaurant);
    }

    @Override
    public String deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = this.restaurantRepository.findRestaurantById(restaurantId);
        this.restaurantRepository.delete(restaurant);
        return "Success";
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> opt = this.restaurantRepository.findById(restaurantId);
        if(opt.isEmpty()) {
            throw new Exception("Restaurant not found");
        };
        return opt.get();
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return this.restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return this.restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = this.restaurantRepository.findByOwnerId(userId);
        if(restaurant == null) {
            throw new Exception("Restaurant not found");
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addFavouriteRestaurant(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = this.restaurantRepository.findRestaurantById(restaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        boolean isFavourited = false;
        List<RestaurantDto> favourites = user.getFavoriteRestaurants();
        for(RestaurantDto favourite : favourites) {
            if(favourite.getId().equals(restaurantId)) {
                isFavourited = true;
                break;
            }
        }

        if(isFavourited) {
            favourites.removeIf(f -> f.getId().equals(restaurantId));
        } else {
            favourites.add(restaurantDto);
        }

        user.setFavoriteRestaurants(favourites);
        this.userRepository.save(user);
        return null;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = this.restaurantRepository.findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return this.restaurantRepository.save(restaurant);
    }
}
