package com.appointment.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class MedicationResponse {
    private long medicationId;
    private String name;
    private String dose;
    private String route;
    private String frequency;
    private String unitOfMeasure;
    private Date startDate;
    private Date endDate;

    private long aid;
    private long pid;
    private long did;
    private long duration;

    public MedicationResponse(long medicationId, String name, String dose, String route, String frequency, String unitOfMeasure, Date startDate, Date endDate, long aid, long pid, long did, long duration) {
        this.medicationId = medicationId;
        this.name = name;
        this.dose = dose;
        this.route = route;
        this.frequency = frequency;
        this.unitOfMeasure = unitOfMeasure;
        this.startDate = startDate;
        this.endDate = endDate;
        this.aid = aid;
        this.pid = pid;
        this.did = did;
        this.duration = duration;
    }

    public MedicationResponse(String noMedicationDetailsAvailable) {
        this.name = noMedicationDetailsAvailable;
    }
}