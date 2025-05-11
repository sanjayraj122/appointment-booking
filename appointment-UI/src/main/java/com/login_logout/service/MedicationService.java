package com.login_logout.service;

import com.login_logout.response.MedicationResponse;

import java.util.List;

public interface MedicationService {

    MedicationResponse createMedicine(MedicationResponse medication);

    MedicationResponse getMedicationByMedicationId(long medicationId);

    List<MedicationResponse> getAllMedicationsByPid(long pid);

    MedicationResponse updateMedication(MedicationResponse medication, long medicationId);

    boolean deleteMedication(long medicationId);

    List<MedicationResponse> getAllMedicationsByDid(long did);
    MedicationResponse getMedicationByDid(long did);

    List<MedicationResponse> getAllMedications(long medicationId);

    List<MedicationResponse> getMedicationsByAid(long aid);

    MedicationResponse getMedicationByAid(long aid);



}
