package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.Cart;
import com.tpt.online_food_order.model.CartItem;
import com.tpt.online_food_order.model.Food;
import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.repository.CartItemRepository;
import com.tpt.online_food_order.repository.CartRepository;
import com.tpt.online_food_order.repository.FoodRepository;
import com.tpt.online_food_order.request.AddCartItemRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    private final UserService userService;

    private final CartItemRepository cartItemRepository;

    private final FoodService foodService;

    public CartServiceImpl(CartRepository cartRepository, UserService userService, com.tpt.online_food_order.repository.CartItemRepository cartItemRepository, FoodService foodService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
        this.foodService = foodService;
    }

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user = this.userService.findUserByJwtToken(jwt);
        Food food = this.foodService.findFoodById(request.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem : cart.getCartItems()) {
            if(cartItem.getFood().getId().equals(food.getId())) {
                return updateItemQuantityInCart(cartItem.getId(), cartItem.getQuantity() + request.getQuantity());
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setIngredients(request.getIngredients());
        cartItem.setTotalPrice(request.getQuantity() * food.getPrice());
        cartItem.setQuantity(1);

        CartItem savedCartItem = this.cartItemRepository.save(cartItem);

        List<CartItem> cartItems = cart.getCartItems();
        cartItems.add(savedCartItem);
        cart.setCartItems(cartItems);
        cartRepository.save(cart);

        return savedCartItem;
    }

    @Override
    public CartItem updateItemQuantityInCart(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if(cartItem.isEmpty()) {
            throw new Exception("CartItem not found");
        }

        CartItem item = cartItem.get();
        double price = item.getTotalPrice() / item.getQuantity();
        item.setQuantity(quantity);
        item.setTotalPrice(price * quantity);
        return this.cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        Optional<CartItem> cartItem = this.cartItemRepository.findById(cartItemId);

        if(cartItem.isEmpty()) {
            throw new Exception("CartItem not found");
        }

        User user = this.userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        CartItem cartItemGet = cartItem.get();
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.remove(cartItemGet);
        cart.setCartItems(cartItems);
        return cartRepository.save(cart);
    }

    @Override
    public double calculateCartTotal(Cart cart) throws Exception {
        double total = 0.0;
        for(CartItem cartItem : cart.getCartItems()) {
            total += cartItem.getTotalPrice();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cart = this.cartRepository.findById(id);
        if(cart.isEmpty()) {
            throw new Exception("CartItem not found");
        }
        return cart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        return cartRepository.findByCustomerId(userId);
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        User user = this.userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(user.getId());
        cart.setCartItems(null);
        cart.setTotalPrice(0.0);
        cart.setTotalQuantity(0);
        return this.cartRepository.save(cart);
    }
}
