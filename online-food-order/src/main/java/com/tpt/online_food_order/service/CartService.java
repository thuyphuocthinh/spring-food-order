package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.Cart;
import com.tpt.online_food_order.model.CartItem;
import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.request.AddCartItemRequest;

import java.util.List;

public interface CartService {
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception;
    public CartItem updateItemQuantityInCart(Long cartItemId, int quantity) throws Exception;
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;
    public double calculateCartTotal(Cart cart) throws Exception;
    public Cart findCartById(Long id) throws Exception;
    public Cart findCartByUserId(Long userId) throws Exception;
    public Cart clearCart(Long userId) throws Exception;
}
