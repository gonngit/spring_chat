package com.spring.chat.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}") // get secret key from application.properties
    private String secretKey;

    private long tokenValidMilisecond = 1000L * 60 * 60; // valid for 1 hour

    // generate JWT token
    public String generateToken(String name) {
        Date now = new Date();
        return Jwts.builder()
                .setId(name)
                .setIssuedAt(now) // token generation time
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set expiration time
                .signWith(SignatureAlgorithm.HS256, secretKey) // set signature algorithm
                // HMAC-SHA256, HMAC : hash-based message authentication code, SHA : Secure Hash Algorithm
                .compact();  // build JWT token in string
    }

    // decrypt JWT token
    public String getUserNameFromJwt(String jwt) {
        return getClaims(jwt).getBody().getId();
    }

    // validate JWT token
    public boolean validateToken(String jwt) {
        return this.getClaims(jwt) != null;
    }
    
    // parse JWT token
    private Jws<Claims> getClaims(String jwt) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        }
    }
}