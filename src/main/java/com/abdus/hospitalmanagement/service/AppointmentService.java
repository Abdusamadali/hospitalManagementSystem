package com.abdus.hospitalmanagement.service;


import com.abdus.hospitalmanagement.DTO.AppointmentDTO;
import com.abdus.hospitalmanagement.entity.Appointment;
import com.abdus.hospitalmanagement.entity.Doctor;
import com.abdus.hospitalmanagement.entity.Patient;
import com.abdus.hospitalmanagement.repository.AppointmentRepository;
import com.abdus.hospitalmanagement.repository.DoctorRepository;
import com.abdus.hospitalmanagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public Appointment createAppointment(AppointmentDTO appointmentDTO) throws Exception {

        Appointment appointment = new Appointment();



            Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId()).orElseThrow(()-> new Exception("Doctor not found"));
            Patient patient = patientRepository.findById(appointmentDTO.getPatientId()).orElseThrow(()->new Exception("Patient not found"));
            appointment.setAppointmentTime(LocalDateTime.now());
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setReason(appointmentDTO.getReason());
//        patient.getAppointments().add(appointment);

            appointmentRepository.save(appointment);
            return appointment;

    }



    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
//        System.out.println("Fetched appointments: " + appointments.size());
//        appointments.forEach(a -> System.out.println(a.getId() + " " + a.getPatient() + " " + a.getDoctor()));
//
        return getAppointmentDTOS(appointments);
    }
    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
        List<Appointment>appnt =  appointmentRepository.findAll()
                .stream().filter(x->x.getDoctor().getId().equals(doctorId)).toList();
        return getAppointmentDTOS(appnt);
    }
    private List<AppointmentDTO> getAppointmentDTOS(List<Appointment> appnt) {
        return appnt.stream().map(x->{
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setId(x.getId());
            appointmentDTO.setReason(x.getReason());
            appointmentDTO.setPatientId(x.getPatient().getId());
            appointmentDTO.setDoctorId(x.getDoctor().getId());
            return appointmentDTO;
        }).toList();
    }


}
