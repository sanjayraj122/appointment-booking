package com.appointment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "doctor_time_slot")
public class DoctorTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "did")
    private Doctor doctor;

    @OneToMany(mappedBy = "doctorSlot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Slot> slots;

}
