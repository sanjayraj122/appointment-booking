package com.appointment.service;

import com.appointment.openfegin.DoctorOpenFegin;
import com.appointment.openfegin.MailOpenFeign;
import com.appointment.openfegin.MedicationOpenFiegn;
import com.appointment.openfegin.PatientOpenFegin;
import com.appointment.response.DoctorResponse;
import com.appointment.response.MailResponse;
import com.appointment.response.MedicationResponse;
import com.appointment.response.PatientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Autowired
    DoctorOpenFegin doctorFegin;

    @Autowired
    PatientOpenFegin patientFegin;

    @Autowired
    MedicationOpenFiegn medicationOpenFiegn;

    @Autowired
    MailOpenFeign mailOpenFeign;

    public DoctorResponse getDoctorById(long did) {
        DoctorResponse doctorResponse = doctorFegin.getById(did);
        return doctorResponse;
    }

    public MailResponse sendEmail(MailResponse mailResponse) {
        return mailOpenFeign.sendEmail(mailResponse);
    }

    public PatientResponse getPatientById(long pid) {
        PatientResponse patientResponse = patientFegin.getById(pid);
        return patientResponse;
    }

    public MedicationResponse getMedicationById(long aid){
        MedicationResponse medicationResponse =medicationOpenFiegn.getMedicationsByAid(aid);

        return medicationResponse;
    }

    public MedicationResponse getByMedicationId(long medicationId) {
        MedicationResponse medicationResponse = medicationOpenFiegn.getByMedicationId(medicationId);
        return medicationResponse;
    }

    public MedicationResponse getMedication(){
        MedicationResponse allMedications = medicationOpenFiegn.getAllMedications();
        return allMedications;

    }
}
