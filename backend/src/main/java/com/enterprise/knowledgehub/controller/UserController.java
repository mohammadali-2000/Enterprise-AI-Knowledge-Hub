package com.enterprise.knowledgehub.controller;

import com.enterprise.knowledgehub.dto.UserRegistrationDTO;
import com.enterprise.knowledgehub.entity.User;
import com.enterprise.knowledgehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Tells Spring: "Make this a Bean! Keep this Waiter in your memory."
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Dependency Injection! Spring gives us the Chef.
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @RequestBody tells Spring to convert the incoming JSON into our safe DTO.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO dto) {
        // The Waiter passes the safe DTO data to the Chef
        User savedUser = userService.registerUser(dto.getEmail(), dto.getRawPassword());
        
        return ResponseEntity.ok("User registered successfully with ID: " + savedUser.getId());
    }
}
