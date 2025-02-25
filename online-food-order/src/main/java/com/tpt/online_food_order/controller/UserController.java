package com.tpt.online_food_order.controller;

import com.tpt.online_food_order.model.User;
import com.tpt.online_food_order.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        String token = jwt.substring(7);
        return new ResponseEntity<>(this.userService.findUserByJwtToken(token), HttpStatus.OK);
    }
}
