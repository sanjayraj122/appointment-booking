package com.login_logout.repo;


import com.login_logout.entity.DoctorTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorTimeSlotRepo extends JpaRepository<DoctorTimeSlot,Long> {

//        Optional<DoctorTimeSlot> findById(long did);
//
//        @Query("SELECT s FROM Slot s WHERE s.doctorSlot.doctor.did = :did AND s.slotDate = :date")
//        List<Slot> findSlotsByDidAndDate(@Param("did") long did, @Param("date") LocalDate date);
//

}


