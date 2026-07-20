package com.enterprise.knowledgehub.config;

import com.enterprise.knowledgehub.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


@Configuration // Tells Spring: "Hey Manager, read these configuration rules on startup"
@EnableWebSecurity // Turns on the Spring Security Bouncer
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // We will use BCrypt to hash passwords in the future
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * This defines the rules for the Bouncer.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF (because we are using stateless JWTs instead of browser cookies)
            .csrf(csrf -> csrf.disable())
            
            // 2. Tell Spring we are completely Stateless (no session cookies)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 3. Define which paths are Public vs Private
            .authorizeHttpRequests(auth -> auth
                // Allow anyone to access the Swagger UI and API Docs
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Allow anyone to Register or Login (you don't need a wristband to GET a wristband)
                .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                
                // EVERY other request in the entire application MUST have a valid JWT wristband
                .anyRequest().authenticated()
            );

        // 4. Place our custom JwtAuthenticationFilter (The Bouncer) in front of the default filters
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
