package com.tpt.online_food_order.controller;

import com.tpt.online_food_order.model.Cart;
import com.tpt.online_food_order.model.CartItem;
import com.tpt.online_food_order.request.AddCartItemRequest;
import com.tpt.online_food_order.request.UpdateCartItemRequest;
import com.tpt.online_food_order.service.CartService;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/add-item")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestBody AddCartItemRequest request,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        return new ResponseEntity<>(
                this.cartService.addItemToCart(request, jwt),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/cart/update-item")
    public ResponseEntity<CartItem> updateItemToCart(
            @RequestBody UpdateCartItemRequest request,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return new ResponseEntity<>(
                this.cartService.updateItemQuantityInCart(request.getCartItemId(), request.getQuantity()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/cart/delete-item/{id}")
    public ResponseEntity<Cart> deleteCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return new ResponseEntity<>(
                this.cartService.removeItemFromCart(id, jwt),
                HttpStatus.OK
        );
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        return new ResponseEntity<>(
                this.cartService.clearCart(jwt),
                HttpStatus.OK
        );
    }
}
