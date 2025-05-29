package com.login_logout.util;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;

    // Getters and setters
}

