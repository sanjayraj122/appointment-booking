package com.mail.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {

	
	private String fullName;

	private String email;

	private String address;

	private String password;
	
	private String role;

}
