package com.abdus.hospitalmanagement.security;

import com.abdus.hospitalmanagement.entity.User;
import com.abdus.hospitalmanagement.entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;


    private SecretKey getSecretKey() {  /// returning encrypted Secret key
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }


//    public static Key getSecretKey() {
//        // Generates a secure 256-bit key for HS256
//        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    }

    //jwt
    public String generateAccessToken(User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId",user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecretKey())
                .compact();

    }

    //jwt
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    //OAuth2
    public AuthProviderType getProviderTypeFromRegistration(String registrationId){
        return switch (registrationId.toLowerCase()){
            case "google"->AuthProviderType.GOOGLE;
            case  "github"->AuthProviderType.GITHUB;
            case "facebook"->AuthProviderType.FACEBOOK;
            default -> throw new IllegalArgumentException("Unsupported OAuth provider ");
        };
    }

    //OAuth2
    public String determineProviderIdFromAuth2User(OAuth2User oAuth2User, String registrationId){
        String providerId = switch (registrationId.toLowerCase()){
            case "google"->oAuth2User.getAttribute("sub");
            case "github" -> Objects.requireNonNull(oAuth2User.getAttribute("id")).toString();
            default -> {
                log.error("Unsupported OAuth2 Provider {}", registrationId);
                throw new IllegalArgumentException("Unsupported OAuth2 Provider: "+registrationId);
            }
        };

        if(providerId ==null || providerId.isBlank()){
            log.error("Unable to determine providerId for OAuth2 login {}",registrationId);
            throw new IllegalArgumentException("Unable to determine providerId for OAuth2 login: "+registrationId);
        }

        return providerId;
    }
//    OAuth2
    public String determineUsernameFromOAuth2User(OAuth2User oAuth2User,String registrationId, String providerId){
        String email = oAuth2User.getAttribute("email");
        if(email == null || !email.isBlank()){
            return email;
        }

        return switch (registrationId.toLowerCase()){
            case "google"->oAuth2User.getAttribute("sub");
            case "github"->oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }

}
