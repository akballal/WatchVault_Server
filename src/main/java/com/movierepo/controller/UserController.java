package com.movierepo.controller;

import com.movierepo.entity.User;
import com.movierepo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
   @Autowired
   private UserService userservice;
    @PostMapping("/signup")
    private ResponseEntity<String> signUp(@RequestBody User user){
        return userservice.signUp(user);
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody User user) {
        return userservice.login(user);
    }

    @PostMapping("/authenticate")
    private ResponseEntity<String> authenticate(@RequestHeader String token)
    {
        return userservice.authenticate(token);
    }
}
