package com.example.blood_donor_finder.service;

import com.example.blood_donor_finder.dto.UserRegisterDTO;
import com.example.blood_donor_finder.dto.UserResponseDTO;
import com.example.blood_donor_finder.entity.User;
import com.example.blood_donor_finder.repository.AuthRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final AuthRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Map<String, String>> registerUser(UserRegisterDTO userRegisterDTO) {
        Map<String, String> response = new HashMap<>();

        // Check if email exists
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            response.put("message", "Email is already registered!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Map DTO to entity
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        // Save user
        userRepository.save(user);
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }


    public UserResponseDTO findByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Email is not found."));
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
