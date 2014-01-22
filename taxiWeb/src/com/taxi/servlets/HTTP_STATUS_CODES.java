package com.taxi.servlets;

public enum HTTP_STATUS_CODES {

	INVALID_CONTENT_TYPE(1001, "Invalid content type"),
	AUTHENTICATION_FAILED(1002, "Authentication Failed");
	
	private final int code;
	private final String message;
	
	HTTP_STATUS_CODES(int code, String message) { this.code = code; this.message = message; }
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
