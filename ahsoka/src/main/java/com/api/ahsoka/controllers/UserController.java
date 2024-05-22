package com.api.ahsoka.controllers;

import com.api.ahsoka.models.UserEntity;
import com.api.ahsoka.services.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserDetailServiceImpl userDetailService;


    @GetMapping("/hello")
    public String hello() {
        return "Hello, your token is valid!";
    }

    @PutMapping("/updateUsername/{id}")
    public UserEntity updateUsername(@PathVariable Long id, @RequestParam String newUsername) {
        return userDetailService.updateUsername(id, newUsername);
    }
}
