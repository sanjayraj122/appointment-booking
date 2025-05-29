package com.appointment.controller;

import com.appointment.entity.Appointment;
import com.appointment.entity.Slot;
import com.appointment.response.AppointmentResponse;
import com.appointment.response.DoctorResponse;
import com.appointment.response.MailResponse;
import com.appointment.response.PatientResponse;
import com.appointment.service.AppointmentService;
import com.appointment.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    CommonService commonService;

    @GetMapping("/getAppsByDid/{did}")
    public List<AppointmentResponse> getAppsByDid(@PathVariable long did) {
        List<AppointmentResponse> appointments = appointmentService.getDoctorApposByDid(did);
        return appointments;
    }

    @GetMapping("/getAppsByPid/{pid}")
    public List<AppointmentResponse> getAppsByPid(@PathVariable long pid) {
        List<AppointmentResponse> appointments = appointmentService.getPatientApposByPid(pid);
        System.out.println("appointment 4" + appointments);
        return appointments;
    }

    @GetMapping("/getAppByID/{id}")
    public AppointmentResponse getAppById(@PathVariable long id) {
        AppointmentResponse appointment = appointmentService.getAppointmentById(id);

        System.out.println(appointment.toString());
        return appointment;

    }

    @PostMapping("/create")
    public AppointmentResponse CreateAppointment(@RequestBody Appointment appointment) {
        // Create the appointment
        AppointmentResponse response = appointmentService.CreateAppointment(appointment);

        // Fetch patient and doctor info
        PatientResponse patient = commonService.getPatientById(response.getPid());
        DoctorResponse doctor = commonService.getDoctorById(response.getDid());

        // Prepare email
        MailResponse mailResponse = new MailResponse();
        mailResponse.setEmailFrom("noReply@gmail.com");
        mailResponse.setEmailTo(patient.getEmail());
        mailResponse.setOwnerRef(doctor.getEmail());
        mailResponse.setSubject("Appointment Confirmation");

        String emailText = "Dear " + patient.getFullName() + ", your appointment has been successfully booked with Dr. " + doctor.getFullName() + ". Please find the appointment details below:\n\n" +
                "Appointment ID: " + response.getAid() + "\n" +
                "Date: " + response.getDate() + "\n" +
                "Time: " + response.getTime() + "\n\n" +
                "Thank you for choosing our service.";

        mailResponse.setText(emailText);

        // Send email
        commonService.sendEmail(mailResponse);

        return response;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AppointmentResponse> EditAppointment(@RequestBody Appointment updatedAppointment, @PathVariable long id) {

        // Fetch existing appointment
        AppointmentResponse existingAppointment = appointmentService.getAppointmentById(id);

        if (existingAppointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Update the appointment fields
        updatedAppointment.setAid(id);
        updatedAppointment.setDid(existingAppointment.getDid());
        updatedAppointment.setPid(existingAppointment.getPid());

        // Save updated appointment
        AppointmentResponse response = appointmentService.CreateAppointment(updatedAppointment);

        // Fetch patient and doctor info
        PatientResponse patient = commonService.getPatientById(response.getPid());
        DoctorResponse doctor = commonService.getDoctorById(response.getDid());

        // Prepare email
        MailResponse mailResponse = new MailResponse();
        mailResponse.setEmailFrom("noReply@gmail.com");
        mailResponse.setEmailTo(patient.getEmail());
        mailResponse.setOwnerRef(doctor.getEmail());
        mailResponse.setSubject("Appointment Updated");

        String emailText = "Dear " + patient.getFullName() + ", your appointment has been updated with Dr. " + doctor.getFullName() + ". Please find the new appointment details below:\n\n" +
                "Appointment ID: " + response.getAid() + "\n" +
                "New Date: " + response.getDate() + "\n" +
                "New Time: " + response.getTime() + "\n\n" +
                "Please be on time.";

        mailResponse.setText(emailText);

        // Send email notification
        commonService.sendEmail(mailResponse);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/detete/{aid}")
    public boolean DeleteAppoointment(@PathVariable long aid) {
        return appointmentService.DeleteAppointment(aid);
    }

    @GetMapping("/status/{aid}/{st}")
    public boolean ChangeStatus(@PathVariable long aid, @PathVariable int st) {

        System.out.println("Appointment--" + aid + st);
        AppointmentResponse appointment = getAppById(aid);
        if (st == 1) {
            appointment.setStatus("Accepted");
        } else {
            appointment.setStatus("Rejected");

        }
        Appointment appo = new Appointment();
        appo.setAid(appointment.getAid());
        appo.setDid(appointment.getDid());
        appo.setPid(appointment.getPid());
        appo.setStatus(appointment.getStatus());
        appo.setDate(appointment.getDate());
        appo.setTime(appointment.getTime());
        CreateAppointment(appo);

        //fetch patient detail
        PatientResponse patient = commonService.getPatientById(appointment.getPid());
        DoctorResponse doctor = commonService.getDoctorById(appointment.getDid());

        // send mail
        MailResponse mailResponse = new MailResponse();
        mailResponse.setEmailFrom("noReply@gmail.com");
        mailResponse.setEmailTo(patient.getEmail());
        mailResponse.setOwnerRef(doctor.getEmail());
        mailResponse.setSubject("Appointment Status");
        if (appointment.getStatus().equals("Accepted")) {
            mailResponse.setText("Dear " + patient.getFullName() + ", your appointment has been accepted with Dr. " + doctor.getFullName() + ". Please find the details below:\n" +
                    "Appointment ID: " + appointment.getAid() + "\n" +
                    "Date: " + appointment.getDate() + "\n" +
                    "Time: " + appointment.getTime() + " . please be on time.");
        } else if (appointment.getStatus().equals("Rejected")) {
            mailResponse.setText("Dear " + patient.getFullName() + ", your appointment has been rejected. Please contact our office for more information.");
        }
        System.out.println("Appointment mail--" + mailResponse.toString());
        commonService.sendEmail(mailResponse);
        return true;
    }

    @GetMapping("/available-slots/{did}")
    public ResponseEntity<List<Slot>> getAvailableSlots(@PathVariable("did") Long did) {
        // Fetch available slots
        List<Slot> availableSlots = appointmentService.getAvailableSlots(did);

        // Check if available slots are found
        if (availableSlots == null || availableSlots.isEmpty()) {
            // Return 404 Not Found if no available slots
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(availableSlots);
        }

        // Return 200 OK with the list of available slots
        return ResponseEntity.ok(availableSlots);
    }

}
