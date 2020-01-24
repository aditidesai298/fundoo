package com.bridgelabz.fundoo.model;

import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;

@Component
public class LoginDto {

	@Email
	private String email;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
