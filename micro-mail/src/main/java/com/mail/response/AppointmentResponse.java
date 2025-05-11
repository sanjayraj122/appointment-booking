package com.mail.response;

import lombok.*;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    private long aid;

    private long did;

    private long pid;

    private Data date;

    private Time time;

    private PatientResponse patientResponse;

    private String status;



}
