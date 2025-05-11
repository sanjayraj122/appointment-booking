package com.patient.response;


import com.patient.entity.DoctorTimeSlot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorResponse {


    private long did;
    private String email;

    private String fullName;
    private String address;

    private String specialization;

    private DoctorTimeSlot doctorTimeSlot;


}
