package com.ga.todoApp.controller;

import com.ga.todoApp.model.User;
import com.ga.todoApp.model.request.LoginRequest;
import com.ga.todoApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/users")
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    public User createUser(@RequestBody User userObj){
        System.out.println("Calling service createUser");
        return userService.createUser(userObj);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        System.out.println("Calling controller loginUser");
        return  userService.loginUser(loginRequest);
    }
}
