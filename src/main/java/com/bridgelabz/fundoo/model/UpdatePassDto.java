package com.bridgelabz.fundoo.model;

public class UpdatePassDto {

	private String emailId;
	private String password;
	private String confirmPassword;


	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "UpdatePassword [emailId=" + emailId + ", password=" + password + ", confirmPassword=" + confirmPassword
				+ "]";
	}

}