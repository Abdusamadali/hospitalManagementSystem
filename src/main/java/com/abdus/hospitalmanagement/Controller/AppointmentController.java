package com.abdus.hospitalmanagement.Controller;


import com.abdus.hospitalmanagement.DTO.AppointmentDTO;

import com.abdus.hospitalmanagement.service.AppointmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointment")
public class AppointmentController {


    private final  AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        List<AppointmentDTO>appointmentDTOList  = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }

    @PostMapping("create-appointment")
    ResponseEntity<?> postAppointment(@RequestBody AppointmentDTO appointmentDTO){
        try {
            appointmentService.createAppointment(appointmentDTO);
            return  new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
