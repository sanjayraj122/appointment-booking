package com.login_logout.service;


import com.login_logout.entity.DoctorTimeSlot;
import com.login_logout.entity.Slot;
import com.login_logout.openfegin.PatientOpenFegin;
import com.login_logout.repo.DoctorRepo;
import com.login_logout.repo.DoctorTimeSlotRepo;
import com.login_logout.repo.PatientRepo;
import com.login_logout.repo.SlotRepo;
import com.login_logout.response.AppointmentResponse;
import com.login_logout.response.DoctorResponse;
import com.login_logout.response.PatientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PatientServiceImp implements PatientService {

    @Autowired
    private PatientRepo userRepo;

    @Autowired
    PatientOpenFegin patientFegin;

    @Autowired
    SlotRepo slotRepo;

    @Autowired
    DoctorTimeSlotRepo doctorTimeSlotRepo;

    @Autowired
    DoctorRepo doctorRepo;

    @Override
    public PatientResponse createUser(PatientResponse patient) {

        return patientFegin.createuser(patient);
    }

    @Override
    public boolean checkEmail(String email) {

        return userRepo.existsByEmail(email);
    }

    @Override
    public PatientResponse getByEmail(String email) {

        return patientFegin.getByEmail(email);
    }

    @Override
    public List<AppointmentResponse> getAllAppointmentsByPid(long pid) {

        List<AppointmentResponse> appointmentResponseList = patientFegin.myAppos(pid);
        System.out.println("appointment 2" + appointmentResponseList);
        return appointmentResponseList;
    }

    @Override
    public List<DoctorResponse> getAllDoctors() {

        return patientFegin.getAllDoctors();
    }

    @Override
    public PatientResponse getById(long id) {

        PatientResponse patientResponse = patientFegin.getById(id);

        return patientResponse;
    }

    @Override
    public PatientResponse updatePatient(PatientResponse patient, long id) {

        return patientFegin.Updatepatient(patient, id);
    }

    @Override
    public DoctorResponse getDoctorByDID(long did) {
        // TODO Auto-generated method stub
        return patientFegin.GetDoctorByDid(did);
    }

    @Override
    public AppointmentResponse CreateAppointment(AppointmentResponse appointmentResponse) {

        return patientFegin.CreateAppointment(appointmentResponse);
    }

    @Override
    public AppointmentResponse getAppointmentByAID(long aid) {

        return patientFegin.getAppointmentByAID(aid);
    }

    @Override
    public boolean DeleteAppointment(long aid) {

        return patientFegin.DeleteAppointment(aid);
    }

    @Override
    public boolean DeletePatient(long id) {

        patientFegin.DeletePatient(id);
        return false;
    }

    @Override
    public boolean UpdateAppoStatus(long aid, int st) {
        // TODO Auto-generated method stub
        patientFegin.ChangeStatus(aid, st);
        return true;
    }



    public List<Slot> getDoctorSlotsForNext7Days(long did) {

        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(7);

        // Fetch booked slots for the doctor within the date range
        List<Slot> bookedSlots = slotRepo.findSlotsByDoctorAndDateRange(did, today, endDate);

        // Create a map to store slots by date for easier processing
        Map<LocalDate, List<Slot>> allSlotsMap = new HashMap<>();

        // Iterate over the next 7 days
        for (LocalDate date = today; !date.isAfter(endDate); date = date.plusDays(1)) {
            final LocalDate currentDate = date;
            List<Slot> slotsForDay = new ArrayList<>();

            // Define startTime based on whether the currentDate is today
            LocalTime startTime;
            if (currentDate.equals(today)) {
                // Round up to the next 30-minute interval
                LocalTime now = LocalTime.now();
                startTime = now.plusMinutes(30 - (now.getMinute() % 30));
            } else {
                startTime = LocalTime.of(9, 0);
            }

            LocalTime endTime = LocalTime.of(18, 0);

            while (!startTime.isAfter(endTime)) {
                LocalTime slotTime = startTime;

                // Check if the slot is booked
                Slot existingSlot = bookedSlots.stream()
                        .filter(slot -> slot.getSlotDate().equals(currentDate) && slot.getSlotTime().equals(slotTime))
                        .findFirst().orElse(null);

                if (existingSlot != null) {
                    slotsForDay.add(existingSlot); // Add booked slot
                } else {
                    // Create an available slot if it does not exist
                    Slot availableSlot = new Slot();
                    DoctorTimeSlot doctorTimeSlot = new DoctorTimeSlot();
                    doctorTimeSlot.setDoctor(doctorRepo.findById(did).get());
                    availableSlot.setSlotDate(currentDate);
                    availableSlot.setSlotTime(slotTime);
                    availableSlot.setStatus("AVAILABLE");
                    availableSlot.setDoctorSlot(doctorTimeSlot);
                    slotsForDay.add(availableSlot);
                }

                // Increment by 30 minutes
                startTime = startTime.plusMinutes(30);
            }

            // Add the list of slots for the day to the map
            allSlotsMap.put(currentDate, slotsForDay);
        }

        // Flatten the map values into a single list to return
        return allSlotsMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

}
