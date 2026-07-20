package com.enterprise.knowledgehub.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // A secret cryptographic key to sign the VIP Wristbands. 
    // In a real enterprise app, this is stored in a secure vault or environment variable.
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    
    // Wristband expiration time: 24 hours
    private final int jwtExpirationMs = 86400000;

    /**
     * The Wristband Generator Machine.
     * Takes an authenticated user and gives them a VIP Wristband (JWT String).
     */
    public String generateToken(Authentication authentication) {
        String userEmail = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * Reads the VIP Wristband and extracts the user's email.
     */
    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Checks if a Wristband is fake, expired, or tampered with.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException ex) {
            // In a real app, you would log this exception
        }
        return false;
    }
}
