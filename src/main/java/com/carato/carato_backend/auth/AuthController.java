package com.carato.carato_backend.auth;

import com.carato.carato_backend.auth.requests.LoginRequest;
import com.carato.carato_backend.auth.requests.RegisterRequest;
import com.carato.carato_backend.auth.responses.LoginResponse;
import com.carato.carato_backend.user.User;
import com.carato.carato_backend.user.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/api/auth/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/auth/register")
    public Map<String, String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @GetMapping("/api/auth/user-info")
    public UserDTO userInfo(@AuthenticationPrincipal User userDetails) {
        return userDetails.getDTO();
    }
}
