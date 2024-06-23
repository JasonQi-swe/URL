package com.example.letmeship.exception;

import com.example.letmeship.entity.DefaultErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn(ex.getMessage());
        DefaultErrorResponse errorResponse = new DefaultErrorResponse("Validation failed", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<DefaultErrorResponse> handleInvalidUrlException(InvalidUrlException ex) {
        logger.warn(ex.getMessage());
        DefaultErrorResponse errorResponse = new DefaultErrorResponse("Invalid URL", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<DefaultErrorResponse> handleTimeoutException(TimeoutException ex) {
        logger.warn(ex.getMessage());
        DefaultErrorResponse errorResponse = new DefaultErrorResponse("Request Timeout", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(PageCannotBeReachedException.class)
    public ResponseEntity<DefaultErrorResponse> handlePageCannotBeReachedException(PageCannotBeReachedException ex) {
        logger.warn(ex.getMessage());
        DefaultErrorResponse errorResponse = new DefaultErrorResponse("Page cannot be reached", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<DefaultErrorResponse> handleIOExceptionn(IOException ex) {
        logger.warn(ex.getMessage());
        DefaultErrorResponse errorResponse = new DefaultErrorResponse("IOException", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
