package com.abdus.hospitalmanagement.repository;

import com.abdus.hospitalmanagement.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    //jpa methods
    List<Patient> findByDob(LocalDate dob);
    Patient findByEmail(String name);

    //jpql
    @Query("select p from Patient p where p.gender = ?1")
    List<Patient> findBYGender(String  gender);


    @Query("select p from Patient  p where p.dob> :birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate date);

//    Page<Patient> findByPage(Pageable pageable);
}
