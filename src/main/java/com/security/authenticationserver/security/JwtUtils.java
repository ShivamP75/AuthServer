package com.security.authenticationserver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class JwtUtils {

    private final String secretKey = "abcdeabcdeijklijklloveloveloveabcdeabcdeijklijkllovelovelove";

    public String generateToken(String username) {
        final long expirationTime = 900L;
        return Jwts.builder().
                subject(username).
                issuedAt(new Date())
                .signWith(key())
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .compact();
    }

    public String getTokenExpirationDateFormatted(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Date expirationDate = claims.getExpiration();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(expirationDate);
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isTokenExpired(String token) {
            Date expireTime  = Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload().getExpiration();
        Date currentTime = new Date();
        return expireTime.after(currentTime);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

}
