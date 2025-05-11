package com.login_logout.response;

import lombok.Data;

@Data
public class SlotResponse {

    private boolean available; // Indicates if the slot is available
    private String message;

    public SlotResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
    }

    public SlotResponse() {
        // Default constructor
    }

    public SlotResponse(boolean isAvailable) {
        this.available = isAvailable;
        this.message = "Slot availability status."; // Default message or you can leave it null
    }
}
