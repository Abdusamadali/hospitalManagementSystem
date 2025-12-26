package com.abdus.hospitalmanagement.security;

import com.abdus.hospitalmanagement.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtAuthUtil {

//    @Value("${jwt.secretKey")
//    private String jwtSecretKey;
//    private SecretKey getSecretKey(){  /// returning encrypted Secret key
//        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
//    }


    public static Key getSecretKey() {
        // Generates a secure 256-bit key for HS256
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateAccessToken(User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId",user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecretKey())
                .compact();

    }
}
