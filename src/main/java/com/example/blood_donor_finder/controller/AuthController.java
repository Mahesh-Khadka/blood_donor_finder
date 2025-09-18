package com.example.blood_donor_finder.controller;

import com.example.blood_donor_finder.dto.UserRegisterDTO;
import com.example.blood_donor_finder.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService userService;

    @Autowired
    public AuthController(AuthService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        return userService.registerUser(userRegisterDTO);
    }
}
