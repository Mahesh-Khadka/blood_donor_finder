package com.example.blood_donor_finder.controller;

import com.example.blood_donor_finder.dto.UserRegisterDTO;
import com.example.blood_donor_finder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        userService.registerUser(userRegisterDTO);
        return ResponseEntity.status(201).body("Registration Successful");
    }
}
