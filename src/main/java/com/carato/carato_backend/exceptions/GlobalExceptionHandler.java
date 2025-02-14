package com.carato.carato_backend.exceptions;

import com.carato.carato_backend.responses.MessageResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public MessageResponse<String> handleHttpMessageNotReadableException() {
        return new MessageResponse<>("error", "Body not received or malformed");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MessageResponse<String[]> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String[] errorMessages = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);

        return new MessageResponse<>("error", errorMessages);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<MessageResponse<String>> handleGeneralExceptions(GeneralException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        String errorMessage = ex.getMessage();
        MessageResponse<String> errorResponse = new MessageResponse<>("error", errorMessage);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
