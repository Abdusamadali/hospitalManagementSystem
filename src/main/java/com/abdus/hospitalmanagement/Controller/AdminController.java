package com.abdus.hospitalmanagement.Controller;


import com.abdus.hospitalmanagement.DTO.AppointmentDTO;
import com.abdus.hospitalmanagement.entity.Doctor;
import com.abdus.hospitalmanagement.entity.Patient;
import com.abdus.hospitalmanagement.repository.DepartmentRepository;
import com.abdus.hospitalmanagement.repository.DoctorRepository;
import com.abdus.hospitalmanagement.repository.PatientRepository;
import com.abdus.hospitalmanagement.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@AllArgsConstructor
public class AdminController {
    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final AppointmentService appointmentService;

    @PostMapping("create-doc")
    ResponseEntity<?> createDoc(@RequestBody Doctor doctor){

        try{
            doctorRepository.save(doctor);
            return ResponseEntity.ok("created doctor successfully");
        }catch(Exception e){
            return new ResponseEntity<>("failed to create doctor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create-patient")
    ResponseEntity<?> createPatient(@RequestBody Patient patient){
        try{
            patientRepository.save(patient);
            return ResponseEntity.ok("created patient successfully");
        }catch(Exception e){
            return new ResponseEntity<>("failed to create patient", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    ResponseEntity<?> getDoctor(@RequestParam Long doctorId){
        return ResponseEntity.ok(doctorRepository.findById(doctorId));
    }

    @GetMapping("/get-doctors")
    ResponseEntity<?> getDoctors(){
        return ResponseEntity.ok(doctorRepository.findAll());
    }

    @GetMapping("/get-apppointments")
    ResponseEntity<?> getAppointment(){
        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

}
