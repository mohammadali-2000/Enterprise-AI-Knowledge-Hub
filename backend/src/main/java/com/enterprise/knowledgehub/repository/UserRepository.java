package com.enterprise.knowledgehub.repository;

import com.enterprise.knowledgehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository // Tells Spring: "Make this a Bean! Keep this pantry in your memory."
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // Spring Boot is so smart, it automatically writes the SQL query for this just by reading the method name!
    // Equivalent to: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);
    
}
