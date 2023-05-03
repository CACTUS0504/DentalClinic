package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "appointments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "appointment_date")
    private Date appointment_date;

    @ManyToOne
    @JoinColumn(name="doctor_id", insertable = false, updatable = false)
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name="doctor_id", insertable = false, updatable = false)
    @JsonIgnore
    private Patient patient;
}