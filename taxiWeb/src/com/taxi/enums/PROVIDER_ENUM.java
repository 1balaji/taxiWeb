package com.taxi.enums;

public enum PROVIDER_ENUM {

	FACEBOOK(1, "http://www.facebook.com"),
	GOOGLE(2, "http://www.google.com");
	
	private final int code;
	private final String message;
	
	PROVIDER_ENUM(int code, String message) { this.code = code; this.message = message; }
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
