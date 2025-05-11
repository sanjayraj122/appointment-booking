package com.doctor.service;

import com.doctor.entity.Doctor;
import com.doctor.repo.DoctorRepo;
import com.doctor.response.DoctorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

	@Autowired
	DoctorRepo doctorRepo;

	@Autowired
	private PasswordEncoder passwordEncode;


	public List<DoctorResponse> getAllDoctors() {
		List<Doctor> doctors = doctorRepo.findAll();
		List<DoctorResponse> doctorResponses = new ArrayList<DoctorResponse>();
		for (Doctor doc : doctors) {
			doctorResponses.add(new DoctorResponse(doc));
		}
		return doctorResponses;
	}

	public DoctorResponse getById(long id) {

		Doctor doctor = doctorRepo.findById(id).get();

		return new DoctorResponse(doctor);

	}

	public DoctorResponse getByEmail(String email) {

		Doctor doctor = doctorRepo.findByEmail(email);

		return new DoctorResponse(doctor);

	}

	public DoctorResponse CreateDoctor(Doctor doctor) {
		doctor.setPassword(passwordEncode.encode(doctor.getPassword()));
		doctor.setRole("ROLE_DOCTOR");
		doctorRepo.save(doctor);

		return new DoctorResponse(doctor);
	}

	public Doctor UpdateDoctor(Doctor doctor) {
		return doctorRepo.save(doctor);
	}

	public String DeleteDoctor(long id) {
		Doctor doctor = doctorRepo.findById(id).get();
		doctorRepo.delete(doctor);
		return "Doctor Account Successfully deleted";
	}


//    public List<DoctorTimeSlot> getDoctorSlotsForNext7Days(Long doctorId) {
//        Date today = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(today);
//        calendar.add(Calendar.DAY_OF_YEAR, 7);
//        Date endDate = calendar.getTime();
//
//        List<DoctorTimeSlot> doctorSlots = doctorRepo.findByDidAndSlotDateBetweenQuery(doctorId, today, endDate);
//        System.out.println("getDoctorSlotsForNext7Days"+doctorSlots);
//        return doctorSlots;
//
//    }

//    public List<DoctorTimeSlot> getDoctorSlotsForSpecificDate(Long doctorId, Date date) {
//        List<DoctorTimeSlot> doctorSlots = doctorRepo.findByDidAndSlotDateQuery(doctorId, date);
//        System.out.println("getDoctorSlotsForSpecificDate"+doctorSlots);
//        return doctorSlots;
//    }

}

