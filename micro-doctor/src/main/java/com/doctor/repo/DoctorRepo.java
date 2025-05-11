package com.doctor.repo;

import com.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {

    public Doctor findByEmail(String email);

//    @Query("SELECT dts FROM Doctor d "
//            + "JOIN d.doctorTimeSlot dts "
//            + "JOIN dts.slots s "
//            + "WHERE d.did = :did AND s.slotDate BETWEEN :startDate AND :endDate")
//    List<DoctorTimeSlot> findByDidAndSlotDateBetweenQuery(@Param("did") long did, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
//
//    @Query("SELECT dts FROM Doctor d "
//            + "JOIN d.doctorTimeSlot dts "
//            + "JOIN dts.slots s "
//            + "WHERE d.did = :did AND s.slotDate = :slotDate")
//    List<DoctorTimeSlot> findByDidAndSlotDateQuery(@Param("did") long did, @Param("slotDate") Date slotDate);


}
