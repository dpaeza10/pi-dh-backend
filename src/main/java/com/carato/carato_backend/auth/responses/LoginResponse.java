package com.carato.carato_backend.auth.responses;

import com.carato.carato_backend.user.UserDTO;
import com.carato.carato_backend.user.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse extends UserDTO {
    private String token;

    public LoginResponse(String token, String username, String email, UserRole role) {
        super(username, email, role);
        this.token = token;
    }
}
