package com.carato.carato_backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GeneralException extends RuntimeException {
    final private HttpStatus httpStatus;

    public GeneralException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public GeneralException(int statusCode, String message) {
        super(message);
        this.httpStatus = HttpStatus.valueOf(statusCode);
    }
}
