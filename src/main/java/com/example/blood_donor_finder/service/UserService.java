package com.example.blood_donor_finder.service;

import com.example.blood_donor_finder.dto.UserRegisterDTO;
import com.example.blood_donor_finder.dto.UserResponseDTO;
import com.example.blood_donor_finder.entity.User;
import com.example.blood_donor_finder.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(UserRegisterDTO userRegisterDTO){
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "User registered Successfully";
    }

    public UserResponseDTO findByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Email is not found."));
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
