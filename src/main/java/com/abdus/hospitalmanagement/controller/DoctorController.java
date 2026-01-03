package com.abdus.hospitalmanagement.controller;


import com.abdus.hospitalmanagement.dto.AppointmentDTO;
import com.abdus.hospitalmanagement.entity.Appointment;
import com.abdus.hospitalmanagement.entity.User;
import com.abdus.hospitalmanagement.repository.DoctorRepository;
import com.abdus.hospitalmanagement.service.AppointmentService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doc")
@AllArgsConstructor
public class DoctorController {

    private final AppointmentService appointmentService;
    private final AuthenticationManager authenticationManager;
    private final DoctorRepository doctorRepository;

    @GetMapping("/get")
    ResponseEntity<?>getResponse(){
        try{
            Long id =((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            List<AppointmentDTO> appointmentsByDoctorId = appointmentService.getAppointmentsByDoctorId(id);
            return ResponseEntity.ok(appointmentsByDoctorId);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("create-appointment")
    ResponseEntity<Appointment>create_appointment(@RequestBody AppointmentDTO appointmentDTO) throws Exception {
        Appointment appointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("delete")
    ResponseEntity <?> delete_appointment(@RequestParam Long id){
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.ok("deleted Appointment: "+id);
    }

}
