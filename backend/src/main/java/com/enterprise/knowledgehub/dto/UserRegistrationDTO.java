package com.enterprise.knowledgehub.dto;

import lombok.Data;

/**
 * The "Takeout Box" for when a user registers.
 * It ONLY contains the exact fields the user is allowed to send us.
 */
@Data
public class UserRegistrationDTO {
    
    private String email;
    private String rawPassword;

}
