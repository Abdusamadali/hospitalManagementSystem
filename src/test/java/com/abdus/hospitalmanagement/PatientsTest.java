package com.abdus.hospitalmanagement;


import com.abdus.hospitalmanagement.entity.Patient;
//import com.abdus.hospitalmanagement.entity.patient;
import com.abdus.hospitalmanagement.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class PatientsTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatients(){

        Patient p = new Patient();
        p.setName("Patient 5");
        p.setDob(LocalDate.of(2006,02,02));
        p.setGender("Male");
        p.setEmail("example@gmail.com");
        patientRepository.save(p);
        List<Patient> patients = patientRepository.findAll();
        System.out.println(patients);
    }

    @Test
    public void testJpql(){
        List<Patient> patients = patientRepository.findBYGender("Male");
        System.out.println(patients);
        List<Patient> patients1 =  patientRepository.findByDob(LocalDate.of(2002,11,5));
        System.out.println(patients1);

        List<Patient> patients2 =  patientRepository.findByBornAfterDate(LocalDate.of(2002,11,10));
        System.out.println("values of born after date : "+patients2);
    }

    @Test
    public void testJpql2(){
        Page<Patient> patients = patientRepository.findAll(PageRequest.of(1, 2));
        System.out.println(patients.getContent());
    }
}
