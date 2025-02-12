package com.carato.carato_backend.auth.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "The username must be required")
    private String username;
    @NotBlank(message = "The password must be required")
    private String password;
}
