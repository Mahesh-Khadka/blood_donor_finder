package com.example.blood_donor_finder.config;

import com.example.blood_donor_finder.entity.User;
import com.example.blood_donor_finder.repository.AuthRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.annotation.PostConstruct;

@Component
public class AdminSetup {

    @Autowired
    private AuthRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdmin() {
        String email = "mahesh7777@gmail.com";

        if (userRepository.findByEmail(email).isEmpty()) {
            User admin = new User();
            admin.setUsername("mahesh7777");
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode("password")); // encrypt
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println("✅ Admin user created!");
        }
    }
}
