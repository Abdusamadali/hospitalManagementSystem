package com.abdus.hospitalmanagement.repository;

import com.abdus.hospitalmanagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}