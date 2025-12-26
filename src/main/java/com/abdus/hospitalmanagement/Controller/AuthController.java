package com.abdus.hospitalmanagement.Controller;

import com.abdus.hospitalmanagement.DTO.LoginRequestDto;
import com.abdus.hospitalmanagement.DTO.LoginResponseDto;
import com.abdus.hospitalmanagement.DTO.SingUpResponseDto;
import com.abdus.hospitalmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

      private final AuthService authService;

      @PostMapping("/login")
      public ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto loginRequestDto){

          return ResponseEntity.ok(authService.login(loginRequestDto));
      }

      @PostMapping("/signup")
    public ResponseEntity<SingUpResponseDto>singUp(@RequestBody LoginRequestDto RequestDto){
          return ResponseEntity.ok(authService.sign(RequestDto));
      }


}
