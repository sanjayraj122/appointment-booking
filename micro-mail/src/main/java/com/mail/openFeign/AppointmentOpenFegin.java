package com.mail.openFeign;

import com.mail.response.AppointmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "api-gateway")
public interface AppointmentOpenFegin {

    @GetMapping("micro-appointment/appointment/getAppsByDid/{did}")
    public List<AppointmentResponse> getAppsByDid(@PathVariable long did);

    @GetMapping("micro-appointment/appointment/status/{aid}/{st}")
    public boolean ChangeStatus(@PathVariable long aid, @PathVariable int st);


    @GetMapping("micro-appointment/appointment/getAppByID/{id}")
    public AppointmentResponse getAppById(@PathVariable long id);
}
