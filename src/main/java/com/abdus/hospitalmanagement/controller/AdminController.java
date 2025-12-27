package com.abdus.hospitalmanagement.controller;

import com.abdus.hospitalmanagement.entity.User;
import com.abdus.hospitalmanagement.entity.Doctor;
import com.abdus.hospitalmanagement.entity.Patient;
import com.abdus.hospitalmanagement.service.AppointmentService;
import com.abdus.hospitalmanagement.service.UserService;
import com.abdus.hospitalmanagement.repository.DoctorRepository;
import com.abdus.hospitalmanagement.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentService appointmentService;
    private final UserService userService;
    @PostMapping("create-doc")
    ResponseEntity<?> createDoc(@RequestBody Doctor doctor) {
        try {
            doctorRepository.save(doctor);
            return ResponseEntity.ok("Created doctor successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create doctor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create-patient")
    ResponseEntity<?> createPatient(@RequestBody Patient patient) {
        try {
            patientRepository.save(patient);
            return ResponseEntity.ok("Created patient successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create patient", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    ResponseEntity<?> getDoctor(@RequestParam Long doctorId) {
        return ResponseEntity.ok(doctorRepository.findById(doctorId));
    }

    @GetMapping("/get-doctors")
    ResponseEntity<?> getDoctors() {
        return ResponseEntity.ok(doctorRepository.findAll());
    }

    @GetMapping("/get-apppointments")
    ResponseEntity<?> getAppointment() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PostMapping("/create-user")
    ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get-users")
    ResponseEntity<?>getUser(){
        List<User> all = userService.getAll();
        return ResponseEntity.ok(all);
    }
}
