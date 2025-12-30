package com.abdus.hospitalmanagement.service;

import com.abdus.hospitalmanagement.dto.LoginRequestDto;
import com.abdus.hospitalmanagement.dto.LoginResponseDto;
import com.abdus.hospitalmanagement.entity.User;
import com.abdus.hospitalmanagement.entity.type.AuthProviderType;
import com.abdus.hospitalmanagement.repository.UserRepository;
import com.abdus.hospitalmanagement.security.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2LoginService {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;

    public User signOAuth2(LoginRequestDto requestDto, AuthProviderType authProviderType, String providerId) {
        if (userRepository.existsByUsernameIgnoreCase((requestDto.getUsername()))){
            throw new IllegalArgumentException("User already exists");
        }



        User user  = User.builder()
                .username(requestDto.getUsername())
//                        .password(passwordEncoder.encode(requestDto.getPassword()))
                .providerId(providerId)
                .providerType(authProviderType)
                .build();


        if(authProviderType == AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(
            OAuth2User oAuth2User, String registrationId) {

        AuthProviderType providerType =
                authUtil.getProviderTypeFromRegistration(registrationId);
        String providerId =
                authUtil.determineProviderIdFromAuth2User(oAuth2User, registrationId);

        String email = oAuth2User.getAttribute("email");

        if(userRepository.existsByUsernameIgnoreCase(email)){
            throw new IllegalArgumentException("User already exist");
        }

        User user =
                userRepository.findByProviderIdAndProviderType(providerId, providerType)
                        .orElse(null);

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if (user == null && emailUser == null) {
            String username =
                    authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);

            user = (User) signOAuth2(
                    new LoginRequestDto(username, null),
                    providerType,
                    providerId
            );

        } else if (user != null) {
            if (email != null && !email.isBlank() && !email.equals(user.getUsername())) {
                user.setUsername(email);
            }
        } else {
            throw new BadCredentialsException(
                    "This email is already registered with provider " + email
            );
        }

        return ResponseEntity.ok(
                new LoginResponseDto(
                        authUtil.generateAccessToken(user),
                        user.getId()
                )
        );
    }

}
