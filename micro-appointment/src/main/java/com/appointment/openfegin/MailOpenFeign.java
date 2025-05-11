package com.appointment.openfegin;


import com.appointment.response.MailResponse;
import com.appointment.response.MedicationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="micro-mail")
public interface MailOpenFeign {

    @PostMapping("/mail/send")
    MailResponse sendEmail(MailResponse mailResponse);

    @PostMapping("/createSchedule")
    MedicationResponse createSchedule(MedicationResponse medicationResponse);

}
