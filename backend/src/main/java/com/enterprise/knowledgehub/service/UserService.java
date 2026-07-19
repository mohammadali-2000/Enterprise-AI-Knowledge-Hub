package com.enterprise.knowledgehub.service;

import com.enterprise.knowledgehub.entity.User;
import com.enterprise.knowledgehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Tells Spring: "Make this a Bean! Keep this Chef in your memory."
public class UserService {

    private final UserRepository userRepository;

    // Dependency Injection! Spring automatically gives us the UserRepository here.
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This is Business Logic. It belongs in the Service layer.
     */
    public User registerUser(String email, String rawPassword) {
        // 1. Enforce business rules
        if (rawPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        // 2. Talk to the Pantry (Database) to see if email exists
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // 3. Create the new User Entity
        User newUser = new User();
        newUser.setEmail(email);
        // Note: In a real app, we MUST hash this password before saving! We will do that later.
        newUser.setPasswordHash(rawPassword); 
        newUser.setRole("USER");

        // 4. Save to the database using the Repository
        return userRepository.save(newUser);
    }
}
