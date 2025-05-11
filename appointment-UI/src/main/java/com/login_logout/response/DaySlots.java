package com.login_logout.response;

import com.login_logout.entity.Slot;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DaySlots {
    private LocalDate date;
    private List<Slot> slots;

    public DaySlots(LocalDate date, List<Slot> slots) {
        this.date = date;
        this.slots = slots;
    }

}

