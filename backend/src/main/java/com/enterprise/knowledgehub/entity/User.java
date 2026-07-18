package com.enterprise.knowledgehub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // This maps exactly to the USERS table in our ERD
@Data // Lombok annotation: Automatically generates getters and setters
@NoArgsConstructor
public class User {

    @Id // This is the Primary Key
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;
    private String passwordHash;
    private String role;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
