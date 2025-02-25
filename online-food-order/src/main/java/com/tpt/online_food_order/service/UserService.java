package com.tpt.online_food_order.service;

import com.tpt.online_food_order.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
}
