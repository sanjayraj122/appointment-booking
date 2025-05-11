package com.appointment.openfegin;

import com.appointment.response.MedicationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "micro-medication")
public interface MedicationOpenFiegn {

    @GetMapping("/medications/getMedicationById/{medicationId}")
    public MedicationResponse getByMedicationId(@PathVariable long medicationId );

    @GetMapping("/medications/getAllMedications")
    public MedicationResponse getAllMedications();

    @GetMapping("/medications/getMedicationsByAid/{aid}")
    public MedicationResponse getMedicationsByAid(@PathVariable long aid) ;

}
