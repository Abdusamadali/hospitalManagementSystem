package com.abdus.hospitalmanagement;


import com.abdus.hospitalmanagement.entity.Appointment;
import com.abdus.hospitalmanagement.entity.Insurance;
import com.abdus.hospitalmanagement.entity.Patient;
import com.abdus.hospitalmanagement.service.AppointmentService;
import com.abdus.hospitalmanagement.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class insuranceServiceTest {


    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    void testInsurance(){
        Insurance insurance = Insurance.builder()
                .policyNumber("4q5989819")
                .provider("HDFC")
                .validUntil(LocalDate.of(2028, 11, 05))
                .build();

        Patient patient = insuranceService.assigningInsuranceToPatients(insurance, 2L);
        System.out.println(patient);

    }

    @Test
    void apointmentTest(){

        Appointment appointment = Appointment.builder()
                .reason("heart attack")
                .appointmentTime(LocalDateTime.of(2025,11,05,4,55))
                .build();

        System.out.println(appointment);

    }
}
