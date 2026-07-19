package com.enterprise.knowledgehub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice // The "Global Complaint Desk"
public class GlobalExceptionHandler {

    /**
     * If ANY code anywhere throws an IllegalArgumentException (like our UserService did),
     * Spring will immediately stop what it is doing and route the error to this method.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        
        // 1. Create our safe Takeout Box
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        // 2. Return a clean 400 Bad Request to the user
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
}
