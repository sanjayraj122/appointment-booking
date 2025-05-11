package com.login_logout.response;

import lombok.Data;

@Data
public class TimeSlotDTO {
    private String time;
    private boolean isDisabled;

    public TimeSlotDTO(String time, boolean isDisabled) {
        this.time = time;
        this.isDisabled = isDisabled;
    }

}

