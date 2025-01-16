package com.nhom3.test.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtil {

    private final static String SECRET_KEY = "dbsahjbdhjasbhjdbashjbdhjasbhjdbshajbdjaswhbdjhasdbhjsabkwb7dbashjbw8bdsajkbdhjkabwhj"; // Use a secure, consistent key

    private final static long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public static String generateToken(String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        System.out.println("Token Expiration Date: " + expirationDate);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

//    public Boolean validateToken(String token) {
//        try {
//            extractClaims(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

//    public Claims extractClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//    }

    public Boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            System.err.println("Invalid JWT signature: " + e.getMessage());
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.err.println("JWT is expired: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("JWT validation failed: " + e.getMessage());
        }
        return false;
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Sử dụng SECRET_KEY đã hardcode
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
