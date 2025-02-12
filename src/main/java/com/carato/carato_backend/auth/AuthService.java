package com.carato.carato_backend.auth;

import com.carato.carato_backend.auth.requests.LoginRequest;
import com.carato.carato_backend.auth.requests.RegisterRequest;
import com.carato.carato_backend.auth.responses.LoginResponse;
import com.carato.carato_backend.exceptions.GeneralException;
import com.carato.carato_backend.jwt.JwtUtil;
import com.carato.carato_backend.user.User;
import com.carato.carato_backend.user.UserRepository;
import com.carato.carato_backend.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = (User) authentication.getPrincipal();
            String token = jwtUtil.getUserToken(user.getDTO());

            return new LoginResponse(token, user.getUsername(), user.getEmail(), user.getRole());
        } catch (AuthenticationException e) {
            throw new GeneralException(401, "Invalid credentials");
        }
    }

    public Map<String, String> register(RegisterRequest registerRequest) {
        try {
            String username = registerRequest.getUsername();
            String email = registerRequest.getEmail();
            String passwordEncoded = passwordEncoder.encode(registerRequest.getPassword());

            User userToSave = new User(null, username, email, passwordEncoded, UserRole.USER);
            userRepository.save(userToSave);

            return Map.of("status", "ok", "message", "User created");
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(409, "The username or email already exists in our database");
        }
    }
}
