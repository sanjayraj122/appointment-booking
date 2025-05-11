package com.appointment.repo;

import com.appointment.entity.DoctorTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorTimeSlotRepo extends JpaRepository<DoctorTimeSlot,Long> {

    List<DoctorTimeSlot> findByDoctor_Did(long did);
}
