package com.carato.carato_backend.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageResponse<T> {
    private String status;
    private T message;
}
