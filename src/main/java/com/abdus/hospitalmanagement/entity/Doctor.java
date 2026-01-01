package com.abdus.hospitalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;

    private String email;




    @ManyToMany(mappedBy = "doctors",fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<Department> departments;
}
