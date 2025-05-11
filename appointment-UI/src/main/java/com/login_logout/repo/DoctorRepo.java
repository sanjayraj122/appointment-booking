package com.login_logout.repo;

import com.login_logout.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DoctorRepo extends JpaRepository<Doctor, Long> {
	
	public boolean existsByEmail(String email);

	public Doctor findByEmail(String email);

}
