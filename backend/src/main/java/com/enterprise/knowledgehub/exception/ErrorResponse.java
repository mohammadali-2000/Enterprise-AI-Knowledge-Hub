package com.enterprise.knowledgehub.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * The "Takeout Box" for Errors.
 * Whenever an error happens, we send this clean JSON object to the user
 * instead of a terrifying HTML stack trace.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    
    private int status;
    private String message;
    private LocalDateTime timestamp;

}
