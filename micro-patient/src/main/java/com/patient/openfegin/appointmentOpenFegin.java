package com.patient.openfegin;

import com.patient.entity.Slot;
import com.patient.response.AppointmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "api-gateway")
public interface appointmentOpenFegin {

    @GetMapping("micro-appointment/appointment/getAppsByPid/{pid}")
    List<AppointmentResponse> getAppsByPid(@PathVariable long pid);

    @PostMapping("micro-appointment/appointment/create")
    AppointmentResponse CreateAppointment(@RequestBody AppointmentResponse appointment);

    @GetMapping("micro-appointment/appointment/getAppByID/{id}")
    AppointmentResponse getAppById(@PathVariable long id);

    @DeleteMapping("micro-appointment/appointment/detete/{aid}")
    boolean DeleteAppoointment(@PathVariable long aid);

    @GetMapping("micro-appointment/appointment/status/{aid}/{st}")
    public boolean ChangeStatus(@PathVariable long aid, @PathVariable int st);

    @GetMapping("micro-appointment/appointment/available-slots/{did}")
    public List<Slot> getAvailableSlots(@PathVariable long id);

}
