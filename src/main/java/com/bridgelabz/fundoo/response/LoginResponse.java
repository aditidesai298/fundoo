package com.bridgelabz.fundoo.response;

import lombok.Data;

@Data
public class LoginResponse {

	String token;
	int status;
	Object obj;

	public LoginResponse(String token, int status, Object obj) {
		super();
		this.token = token;
		this.status = status;
		this.obj = obj;
	}

}
