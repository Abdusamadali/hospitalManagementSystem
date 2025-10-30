package com.abdus.hospitalmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = true,unique = true )
    private String name;

    @OneToOne
    private Doctor doctorId;

    @ManyToMany   /// it will create a joint table by default  that will hold the many to many relationships bitween them
    @JoinTable( // we can explicitly give name and other property by using joinTable annotation
            name = "my_dpt_doctor_join_Table",
            joinColumns = @JoinColumn(name = "dpt_id"),// owning side    //by default it will create but we can explicitly tell hibernate to set these attributes
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private Set<Doctor> doctors = new HashSet<>();


}
