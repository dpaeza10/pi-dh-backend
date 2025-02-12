package com.carato.carato_backend.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleHttpMessageNotReadableException() {
        return Map.of("status", "error", "message", "Body not received or malformed");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String[] errorMessages = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);

        return Map.of("status", "error", "message", errorMessages);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Map<String, String>> handleGeneralExceptions(GeneralException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        String errorMessage = ex.getMessage();
        Map<String, String> errorResponse = Map.of("status", "error", "message", errorMessage);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
