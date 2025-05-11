package com.login_logout.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MedicationResponse {

    private long medicationId;
    private String name;
    private String dose;
    private String route;
    private String frequency;
    private String unitOfMeasure;
    private Time time;
    private Date startDate;
    private Date endDate;

    private long aid;
    private long pid;
    private long did;
    private long duration;
    private PatientResponse patient;

    private MedicationResponse medicationResponse;
    public boolean alert;

    public MedicationResponse(String noMedicationDetailsAvailable) {
        this.name = noMedicationDetailsAvailable;
    }

    public long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(long medicationId) {
        this.medicationId = medicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public PatientResponse getPatient() {
        return patient;
    }

    public void setPatient(PatientResponse patient) {
        this.patient = patient;
    }

    public MedicationResponse getMedicationResponse() {
        return medicationResponse;
    }

    public void setMedicationResponse(MedicationResponse medicationResponse) {
        this.medicationResponse = medicationResponse;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    @Override
    public String toString() {
        return "MedicationResponse{" +
                "medicationId=" + medicationId +
                ", name='" + name + '\'' +
                ", dose='" + dose + '\'' +
                ", route='" + route + '\'' +
                ", frequency='" + frequency + '\'' +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", time=" + time +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", aid=" + aid +
                ", pid=" + pid +
                ", did=" + did +
                ", duration=" + duration +
                ", patient=" + patient +
                ", medicationResponse=" + medicationResponse +
                ", alert=" + alert +
                '}';
    }
}