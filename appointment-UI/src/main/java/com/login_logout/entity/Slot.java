package com.login_logout.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "doctor_slot_id")
    private DoctorTimeSlot doctorSlot;

    @Column(name = "slot_date")
    private LocalDate slotDate;

    @Column(name = "slot_time")
    private LocalTime slotTime;

    @Column(name = "status")
    private String status; // EMPTY, BOOKED, RESERVED, etc.

    @Column(name = "appointment_id")
    private long aid;
}
