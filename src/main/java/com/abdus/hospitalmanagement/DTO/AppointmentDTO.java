package com.abdus.hospitalmanagement.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.util.List;

@Data
public class AppointmentDTO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;
    private Long doctorId;
    private Long patientId;
}
