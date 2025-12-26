package com.abdus.hospitalmanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SingUpResponseDto {
    private Long id;
    private String username;
}
