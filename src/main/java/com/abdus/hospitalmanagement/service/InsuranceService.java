package com.abdus.hospitalmanagement.service;


import com.abdus.hospitalmanagement.entity.Insurance;
import com.abdus.hospitalmanagement.entity.Patient;
import com.abdus.hospitalmanagement.repository.InsuranceRepository;
import com.abdus.hospitalmanagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {



    private final  InsuranceRepository insuranceRepository;

    private final PatientRepository patientRepository;

    @Transactional
    public Patient assigningInsuranceToPatients(Insurance insurance, Long patientId) {
            Patient patient1 = patientRepository.findById(patientId).orElseThrow(()->new EntityNotFoundException("Patient not found"));

            patient1.setInsurance(insurance);
            insurance.setPatient(patient1);

        return patient1;
    }

}


/*
| Injection Type   | Annotation            | Pros                         | Cons                      |
        | ---------------- | --------------------- | ---------------------------- | ------------------------- |
        | Constructor      | `@Autowired` optional | Immutable, testable, clean   | Slightly more boilerplate |
        | Field            | `@Autowired`          | Simple                       | Not testable, not final   |
        | Setter           | `@Autowired`          | Optional dependencies        | Mutable, extra setter     |
        | Qualifier        | `@Qualifier`          | Specify exact implementation | Only when multiple beans  |
        | Standard (`JSR`) | `@Inject`             | Standard Java annotation     | Same as `@Autowired`      |
        | Resource (`JSR`) | `@Resource`           | Inject by bean name          | Not type-safe             |
*/