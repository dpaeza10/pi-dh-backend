package com.carato.carato_backend.jwt;

import com.carato.carato_backend.exceptions.GeneralException;
import com.carato.carato_backend.user.UserDTO;
import com.carato.carato_backend.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    final private SecretKey key;

    public JwtUtil() {
        // this.key = Jwts.SIG.HS256.key().build();
        String seed = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
        this.key = Keys.hmacShaKeyFor(seed.getBytes());
    }

    public String getUserToken(UserDTO userDTO) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + 3600 * 1000 * 24);

        return Jwts
                .builder()
                .claim("username", userDTO.getUsername())
                .claim("email", userDTO.getEmail())
                .claim("role", userDTO.getRole())
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public UserDTO getUserFromToken(String token) throws JwtException, IllegalArgumentException {
        Claims payload = Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        String username = payload.get("username", String.class);
        String email = payload.get("email", String.class);
        UserRole role = UserRole.valueOf(payload.get("role", String.class));
        return new UserDTO(username, email, role);
    }
}
