package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.*;
import com.tpt.online_food_order.repository.*;
import com.tpt.online_food_order.request.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final UserService userService;

    private final CartService cartService;

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserService userService, CartService cartService, AddressRepository addressRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Order createOrder(OrderRequest request, User user) throws Exception {
        Address deliveryAddress = request.getDeliveryAddress();
        Address savedAddress = this.addressRepository.save(deliveryAddress);

        if(!user.getAddresses().contains(deliveryAddress)){
            user.getAddresses().add(deliveryAddress);
            this.userRepository.save(user);
        }

        Restaurant restaurant = this.restaurantRepository.findRestaurantById(request.getRestaurantId());

        Order order = new Order();
        order.setDeliveryAddress(deliveryAddress);
        order.setRestaurant(restaurant);
        order.setCreatedDate(new Date());
        order.setOrderStatus("PENDING");
        order.setCustomer(user);

        Cart cart = this.cartService.findCartByUserId(user.getId());
        List<OrderItem> listOrderItems = new ArrayList<>();
        double totalPrice = 0.0;
        int totalQuantity = 0;
        for(CartItem cartItem : cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setIngredients(cartItem.getIngredients());
            listOrderItems.add(orderItem);
            totalPrice += orderItem.getTotalPrice();
            totalQuantity += orderItem.getQuantity();
            this.orderItemRepository.save(orderItem);
        }

        order.setItems(listOrderItems);
        order.setTotalPrice(totalPrice);
        order.setTotalItem(totalQuantity);
        return this.orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Optional<Order> order = this.orderRepository.findById(orderId);
        if(order.isEmpty()) {
            throw new Exception("Order not found");
        }

        Order orderGet = order.get();
        orderGet.setOrderStatus(orderStatus);

        return this.orderRepository.save(orderGet);
    }

    @Override
    public String cancelOrder(Long orderId) throws Exception {
        Optional<Order> order = this.orderRepository.findById(orderId);
        if(order.isEmpty()) {
            throw new Exception("Order not found");
        }
        Order orderGet = order.get();
        this.orderRepository.delete(orderGet);
        return "Successfully cancelled order";
    }

    @Override
    public List<Order> getOrdersOfUser(Long userId) throws Exception {
        return this.orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        return this.orderRepository.findByRestaurantIdAndOrderStatus(restaurantId, orderStatus);
    }
}
