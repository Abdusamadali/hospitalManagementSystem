package com.abdus.hospitalmanagement.service;


import com.abdus.hospitalmanagement.entity.Appointment;
import com.abdus.hospitalmanagement.entity.Doctor;
import com.abdus.hospitalmanagement.entity.Patient;
import com.abdus.hospitalmanagement.repository.AppointmentRepository;
import com.abdus.hospitalmanagement.repository.DoctorRepository;
import com.abdus.hospitalmanagement.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public void createAppointment(Appointment appointment, Long doctorId, Long patientId) {

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

//        patient.getAppointments().add(appointment);

        appointmentRepository.save(appointment);


    }
}
