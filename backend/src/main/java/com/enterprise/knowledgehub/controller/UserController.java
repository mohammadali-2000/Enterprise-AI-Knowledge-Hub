package com.enterprise.knowledgehub.controller;

import com.enterprise.knowledgehub.dto.UserRegistrationDTO;
import com.enterprise.knowledgehub.dto.UserLoginDTO;
import com.enterprise.knowledgehub.dto.LoginResponseDTO;
import com.enterprise.knowledgehub.entity.User;
import com.enterprise.knowledgehub.service.UserService;
import com.enterprise.knowledgehub.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController // Tells Spring: "Make this a Bean! Keep this Waiter in your memory."
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    // The Waiter asks Spring to inject the Chef, the AuthManager, and the TokenMachine
    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    /**
     * @RequestBody tells Spring to convert the incoming JSON into our safe DTO.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        User savedUser = userService.registerUser(registrationDTO.getEmail(), registrationDTO.getRawPassword());
        return ResponseEntity.ok("User registered successfully with ID: " + savedUser.getId());
    }

    /**
     * Endpoint for users to "Show their ID" to the Bouncer and get a VIP Wristband (JWT).
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@Valid @RequestBody UserLoginDTO loginDto) {
        // 1. Tell Spring Security to verify the email and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getRawPassword()
                )
        );

        // 2. If successful, officially log them in to the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generate the VIP Wristband (JWT)
        String jwt = tokenProvider.generateToken(authentication);
        
        // 4. Give the wristband to the user
        return ResponseEntity.ok(new LoginResponseDTO(jwt, "Bearer"));
    }
}
