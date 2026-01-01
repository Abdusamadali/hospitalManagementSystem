package com.abdus.hospitalmanagement.service;

import com.abdus.hospitalmanagement.dto.LoginRequestDto;
import com.abdus.hospitalmanagement.dto.LoginResponseDto;
import com.abdus.hospitalmanagement.dto.SignUpRequestDto;
import com.abdus.hospitalmanagement.dto.SingUpResponseDto;
import com.abdus.hospitalmanagement.entity.Patient;
import com.abdus.hospitalmanagement.entity.User;
import com.abdus.hospitalmanagement.entity.type.Role;
import com.abdus.hospitalmanagement.repository.PatientRepository;
import com.abdus.hospitalmanagement.repository.UserRepository;
import com.abdus.hospitalmanagement.security.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        User user = (User)authenticate.getPrincipal();
        String token = authUtil.generateAccessToken(user);
        return new LoginResponseDto(token,user.getId());
    }


    @Transactional
    public SingUpResponseDto sign(SignUpRequestDto requestDto) {
        if (userRepository.existsByUsernameIgnoreCase((requestDto.getUsername()))){
            throw new IllegalArgumentException("User already exists");
        }

        User save = userRepository.save(
                User.builder().username(requestDto.getUsername())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .roles(Set.of(Role.PATIENT))
                        .build()
        );

        Patient p = Patient.builder()
                .name(requestDto.getUsername())
                .email(requestDto.getEmail())
                .user(save)
                .build();
        patientRepository.save(p);

        return new SingUpResponseDto(save.getId(), save.getUsername());
    }

}
