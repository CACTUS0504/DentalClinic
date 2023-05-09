package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @OneToMany(mappedBy = "patient")
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
