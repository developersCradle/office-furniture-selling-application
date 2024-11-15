package com.officesales.office_furniture_sales.globalerrorhandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.officesales.office_furniture_sales.exception.BadRequestException;
import com.officesales.office_furniture_sales.exception.ErrorResponse;
import com.officesales.office_furniture_sales.exception.ResourceNotFoundException;

/*
 * Add here when new exception comes.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {

    	
        ErrorResponse errorResponse = new ErrorResponse("error", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {

    	ErrorResponse errorResponse = new ErrorResponse("error", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Catch generic error response.
    @ExceptionHandler(Exception.class) // Gonna catch the all pokemon!
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    	
        ex.printStackTrace(); // Optional.

        ErrorResponse errorResponse = new ErrorResponse("error", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
