package com.abdus.hospitalmanagement.service;

import com.abdus.hospitalmanagement.dto.LoginRequestDto;
import com.abdus.hospitalmanagement.dto.LoginResponseDto;
import com.abdus.hospitalmanagement.dto.SingUpResponseDto;
import com.abdus.hospitalmanagement.entity.User;
import com.abdus.hospitalmanagement.repository.UserRepository;
import com.abdus.hospitalmanagement.security.JwtAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final JwtAuthUtil jwtAuthUtil;
    private final UserRepository userRepository;


    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        User user = (User)authenticate.getPrincipal();
        String token = jwtAuthUtil.generateAccessToken(user);
        return new LoginResponseDto(token,user.getId());
    }

    public SingUpResponseDto sign(LoginRequestDto requestDto) {
        if (userRepository.existsByUsername((requestDto.getUsername()))){
            throw new IllegalArgumentException("User already exists");
        }

        User save = userRepository.save(
                User.builder().username(requestDto.getUsername())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .build()
        );

        return new SingUpResponseDto(save.getId(), save.getUsername());
    }



}
