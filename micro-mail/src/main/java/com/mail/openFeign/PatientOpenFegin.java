package com.mail.openFeign;

import com.mail.response.AppointmentResponse;
import com.mail.response.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "micro-patient")
public interface PatientOpenFegin {

    @GetMapping("/patient/getByEmail/{email}")
    PatientResponse getByEmail(@PathVariable String email);

//    @GetMapping("/patient/AppByAid/{aid}")
//    AppointmentResponse getAppointmentByAID(@PathVariable long aid);

    @GetMapping("/Apps/{pid}")
    AppointmentResponse getAppointmentByPid(@PathVariable long pid);

}