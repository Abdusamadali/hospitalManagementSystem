package com.abdus.hospitalmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "appointments")
@Table(name = "patient")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    private LocalDate dob;


    private String gender;

    @OneToOne
    @MapsId
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance; //owning side

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL )
    private List<Appointment> appointments;


}
