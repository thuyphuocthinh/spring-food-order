package com.tpt.online_food_order.controller;

import com.tpt.online_food_order.model.Order;
import com.tpt.online_food_order.request.OrderRequest;
import com.tpt.online_food_order.response.MessageResponse;
import com.tpt.online_food_order.service.OrderService;
import com.tpt.online_food_order.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderRequest order,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return new ResponseEntity<>(
                this.orderService.createOrder(
                        order,
                        this.userService.findUserByJwtToken(jwt)
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/admin/orders/{id}/update-status/{status}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String status
    ) throws Exception {
        return new ResponseEntity<>(
                this.orderService.updateOrder(
                        id,
                        status
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/orders/{id}/cancel")
    public ResponseEntity<MessageResponse> cancelOrder(
            @PathVariable Long id
    ) throws Exception {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(this.orderService.cancelOrder(id));
        return new ResponseEntity<>(
                messageResponse,
                HttpStatus.OK
        );
    }

    @GetMapping("/orders/users/{id}")
    public ResponseEntity<List<Order>> getAllOrders(
            @PathVariable Long id
    ) throws Exception {
        return new ResponseEntity<>(
                this.orderService.getOrdersOfUser(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/orders/status/{status}/restaurants/{id}")
    public ResponseEntity<List<Order>> getAllRestaurantOrders(
            @PathVariable Long id,
            @PathVariable String status
    ) throws Exception {
        return new ResponseEntity<>(
                this.orderService.getRestaurantOrders(id, status),
                HttpStatus.OK
        );
    }
}
