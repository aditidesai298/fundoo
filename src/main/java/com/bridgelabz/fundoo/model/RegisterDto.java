package com.bridgelabz.fundoo.model;

import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class RegisterDto {

	private String fname;
	private String lname;
	@Email
	private String email;
	private String password;
	private String mob_number;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMob_number() {
		return mob_number;
	}

	public void setMob_number(String mob_number) {
		this.mob_number = mob_number;
	}

}
