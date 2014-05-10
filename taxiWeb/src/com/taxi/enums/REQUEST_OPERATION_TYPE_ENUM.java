package com.taxi.enums;

public enum REQUEST_OPERATION_TYPE_ENUM {

	
	REUESTPAYMENTAMOUNT(1, "Count Request Payment Amount");
	
	private final int code;
	private final String message;
	
	REQUEST_OPERATION_TYPE_ENUM(int code, String message) { this.code = code; this.message = message; }
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
