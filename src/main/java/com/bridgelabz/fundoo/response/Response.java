package com.bridgelabz.fundoo.response;

import java.util.List;

public class Response {

	private String message;

	private int statusCode;

	private Object object;

	List<String> details;

	public Response(String message, int statusCode, Object object) {
		this.message = message;
		this.statusCode = statusCode;
		this.object = object;
	}

	public Response(String message, int statusCode) {

		this.message = message;
		this.statusCode = statusCode;
	}

	public Response() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getObj() {
		return object;
	}

	public void setObj(Object obj) {
		this.object = obj;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Response [message=" + message + ", statusCode=" + statusCode + ", object=" + object + ", details="
				+ details + "]";
	}

}