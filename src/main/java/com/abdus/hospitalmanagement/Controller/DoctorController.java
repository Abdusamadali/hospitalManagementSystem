package com.abdus.hospitalmanagement.Controller;


import com.abdus.hospitalmanagement.DTO.AppointmentDTO;
import com.abdus.hospitalmanagement.service.AppointmentService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doc")
@AllArgsConstructor
public class DoctorController {

    private final AppointmentService appointmentService;

    @GetMapping("/get")
    ResponseEntity<?>getResponse(@RequestParam Long doctorId){
        try{
            List<AppointmentDTO> appointmentsByDoctorId = appointmentService.getAppointmentsByDoctorId(doctorId);
            System.out.println(doctorId);
            return ResponseEntity.ok(appointmentsByDoctorId);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


}
