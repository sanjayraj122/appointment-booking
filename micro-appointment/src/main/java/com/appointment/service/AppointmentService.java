package com.appointment.service;

import com.appointment.entity.Appointment;
import com.appointment.entity.DoctorTimeSlot;
import com.appointment.entity.Slot;
import com.appointment.repo.AppointmentRepo;
import com.appointment.repo.DoctorTimeSlotRepo;
import com.appointment.response.AppointmentResponse;
import com.appointment.response.DoctorResponse;
import com.appointment.response.MedicationResponse;
import com.appointment.response.PatientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepo appointmentRepo;

    @Autowired
    DoctorTimeSlotRepo doctorTimeSlotRepo;

    @Autowired
    CommonService commonService;

//    @Autowired
//    DoctorOpenFegin doctorOpenFegin;

    public List<AppointmentResponse> getDoctorApposByDid(long did) {

        List<Appointment> appointment = appointmentRepo.getByDid(did);
        List<AppointmentResponse> appointmentResponse = new ArrayList<AppointmentResponse>();

        for (Appointment appo : appointment) {

            AppointmentResponse appoRes = new AppointmentResponse(appo);

            PatientResponse patientResponse = commonService.getPatientById(appo.getPid());

            appoRes.setPatientResponse(patientResponse);
            appointmentResponse.add(appoRes);
        }
        return appointmentResponse;
    }

    public List<AppointmentResponse> getPatientApposByPid(long pid) {

        List<Appointment> appointment = appointmentRepo.getByPid(pid);
        List<AppointmentResponse> appointmentResponse = new ArrayList<AppointmentResponse>();

        for (Appointment appo : appointment) {

            AppointmentResponse appoRes = new AppointmentResponse(appo);
            MedicationResponse medicationResponse;

            try {
                medicationResponse = commonService.getByMedicationId(appo.getAid());
            } catch (Exception e) {
                medicationResponse = null;
            }

            DoctorResponse doctorResponse = commonService.getDoctorById(appo.getDid());
            appoRes.setDoctorResponse(doctorResponse);
            appointmentResponse.add(appoRes);
        }
        return appointmentResponse;

    }

    public AppointmentResponse getAppointmentById(long id) {

        Appointment appointment = appointmentRepo.findById(id).get();
        DoctorResponse doctorResponse = commonService.getDoctorById(appointment.getDid());
        PatientResponse patientResponse = commonService.getPatientById(appointment.getPid());
        MedicationResponse medicationResponse = commonService.getMedicationById(appointment.getAid());

        AppointmentResponse appointmentResponse = new AppointmentResponse(appointment);
        appointmentResponse.setDoctorResponse(doctorResponse);
        appointmentResponse.setMedicationResponse(medicationResponse);
        appointmentResponse.setPatientResponse(patientResponse);
        System.out.println("appointment service 5" + appointmentResponse);
        return appointmentResponse;
    }

    public AppointmentResponse CreateAppointment(Appointment appointment) {
        appointment = appointmentRepo.save(appointment);
        return new AppointmentResponse(appointment);
    }

    public boolean DeleteAppointment(long aid) {
        Appointment appo = appointmentRepo.findById(aid).get();
        appointmentRepo.delete(appo);
        if (appointmentRepo.existsById(aid))
            return false;
        return true;
    }

    // To fetch available slots for a doctor within the next 7 days
    public List<Slot> getAvailableSlots(long did) {
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.plusDays(7);

        // Fetch doctor time slots for the given doctor ID
        List<DoctorTimeSlot> doctorTimeSlots = doctorTimeSlotRepo.findByDoctor_Did(did); // Assuming a custom method in DoctorTimeSlotRepo
        List<Slot> availableSlots = new ArrayList<>();

        // Loop through the doctor's time slots and filter for available slots
        for (DoctorTimeSlot doctorTimeSlot : doctorTimeSlots) {
            if (doctorTimeSlot.getSlots() != null && !doctorTimeSlot.getSlots().isEmpty()) {
                List<Slot> slots = doctorTimeSlot.getSlots().stream()
                        .filter(slot -> !slot.getSlotDate().isBefore(currentDate) && !slot.getSlotDate().isAfter(endDate))
                        .filter(slot -> "EMPTY".equals(slot.getStatus())) // Compare strings
                        .collect(Collectors.toList());

                availableSlots.addAll(slots);
            }
        }

        return availableSlots;
    }

}




