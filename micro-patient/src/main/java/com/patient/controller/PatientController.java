package com.patient.controller;


import com.patient.entity.Patient;
import com.patient.entity.Slot;
import com.patient.response.AppointmentResponse;
import com.patient.response.DoctorResponse;
import com.patient.response.PatientResponse;
import com.patient.service.CommonService;
import com.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    CommonService commonService;

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or (hasRole('PATIENT') and #id == @patientService.getPatientIdByEmail(authentication.name))")
    public ResponseEntity<Object> getById(@PathVariable long id, Authentication authentication) {
        return new ResponseEntity<>(new PatientResponse(patientService.getById(id)), HttpStatus.OK);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public PatientResponse createuser(@RequestBody Patient patient) {
        Patient newPatient = patientService.createUser(patient);
        PatientResponse patientResponse = new PatientResponse(newPatient);
        return patientResponse;
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PATIENT') and #id == @patientService.getPatientIdByEmail(authentication.name))")
    public PatientResponse Updatepatient(@RequestBody Patient patient, @PathVariable long id, Authentication authentication) {
        patient.setId(id);
        Patient patientNew = patientService.UpdatePatient(patient);
        PatientResponse patientResponse = new PatientResponse(patientNew);
        return patientResponse;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean DeletePatient(@PathVariable long id) {
        return patientService.DeletePatient(id);
    }

    @GetMapping("/getByEmail/{email}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or (hasRole('PATIENT') and #email == authentication.name)")
    public PatientResponse getByEmail(@PathVariable String email, Authentication authentication) {
        return patientService.getByEmail(email);
    }

    @GetMapping("/Apps/{pid}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or (hasRole('PATIENT') and #pid == @patientService.getPatientIdByEmail(authentication.name))")
    public List<AppointmentResponse> myAppos(@PathVariable long pid, Authentication authentication) {
        return commonService.PatientAppointment(pid);
    }

    @GetMapping("/AppByAid/{aid}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
    public AppointmentResponse getAppointmentByAID(@PathVariable long aid) {
        AppointmentResponse appointment = commonService.GetPaaointmentByAID(aid);
        System.out.println(appointment.toString());
        return appointment;
    }

    @GetMapping("/doctors")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
    public List<DoctorResponse> getAllDoctors() {
        return commonService.getAllDoctors();
    }

    @GetMapping("/book/{did}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
    public DoctorResponse GetDoctorByDid(@PathVariable long did) {
        return commonService.getDoctorByDID(did);
    }

    @PostMapping("/book")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public AppointmentResponse CreateAppointment(@RequestBody AppointmentResponse appointmentResponse) {
        // Ensure patient can only book appointments for themselves
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"))) {
            long patientId = patientService.getPatientIdByEmail(auth.getName());
            appointmentResponse.setPatientId(patientId);
        }
        return commonService.CreateAppointment(appointmentResponse);
    }

    @DeleteMapping("/deleteAppointemt/{aid}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public boolean DeleteAppointment(@PathVariable long aid) {
        return commonService.DeteleAppointment(aid);
    }

    @GetMapping("/status/{aid}/{st}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public boolean ChangeStatus(@PathVariable long aid, @PathVariable int st) {
        commonService.ChangeStatus(aid, st);
        return true;
    }

    @GetMapping("/available-slots/{did}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
    public ResponseEntity<List<Slot>> getAvailableSlots(@PathVariable long did) {
        List<Slot> availableSlots = commonService.getAvailableSlots(did);
        return ResponseEntity.ok(availableSlots);
    }

}
