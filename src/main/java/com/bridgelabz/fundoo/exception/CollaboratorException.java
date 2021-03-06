package com.bridgelabz.fundoo.exception;

public class CollaboratorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final int status;

	public CollaboratorException(String message, int status) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}