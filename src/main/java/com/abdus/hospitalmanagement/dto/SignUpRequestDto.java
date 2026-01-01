package com.abdus.hospitalmanagement.dto;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignUpRequestDto {

    private String username;
    private String email;
    private String password;
}
