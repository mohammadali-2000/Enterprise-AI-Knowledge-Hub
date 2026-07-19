package com.enterprise.knowledgehub.dto;

import lombok.Data;
import java.util.UUID;

/**
 * The "Takeout Box" for when we send user data BACK to the web browser.
 * Notice we do NOT include the passwordHash!
 */
@Data
public class UserProfileDTO {
    
    private UUID id;
    private String email;
    private String role;

}
