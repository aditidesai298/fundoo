package com.bridgelabz.fundoo.response;

import com.bridgelabz.fundoo.dto.LoginDto;

import lombok.Data;

@Data
public class LoginResponse {

	String token;
	int status;
	LoginDto dto;

	public LoginResponse(String token, int status, LoginDto dto) {
		super();
		this.token = token;
		this.status = status;
		this.dto = dto;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LoginDto getDto() {
		return dto;
	}

	public void setDto(LoginDto dto) {
		this.dto = dto;
	}

}
