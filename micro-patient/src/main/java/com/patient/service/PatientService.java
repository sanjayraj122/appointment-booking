package com.patient.service;

import com.patient.entity.Patient;
import com.patient.repo.PatientRepository;
import com.patient.response.PatientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepo;

    @Autowired
    private PasswordEncoder passwordEncode;


    //Getting patient details by id
    public Patient getById(long id) {
        Patient patient = patientRepo.findById(id).get();
        return patient;
    }

    public PatientResponse getByEmail(String email) {
        Patient patient = patientRepo.findByEmail(email);
        return new PatientResponse(patient);
    }

    public long getPatientIdByEmail(String email) {
        Patient patient = patientRepo.findByEmail(email);
        return patient != null ? patient.getId() : -1;
    }

    //To create the new Patient
    public Patient createUser(Patient patient) {
        patient.setPassword(passwordEncode.encode(patient.getPassword()));
        patient.setRole("ROLE_PATIENT");
        return patientRepo.save(patient);
    }

    public boolean DeletePatient(long id) {
        Patient patient = patientRepo.findById(id).get();
        patientRepo.delete(patient);

        return !(patientRepo.existsById(id));
    }

    //To create the Update Patient
    public Patient UpdatePatient(Patient patient) {
        Patient patientNew = getById(patient.getId());
        patient.setPassword(patientNew.getPassword());

        patient.setRole("ROLE_PATIENT");
        return patientRepo.save(patient);
    }

}
