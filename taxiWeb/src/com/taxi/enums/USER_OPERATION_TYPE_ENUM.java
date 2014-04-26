package com.taxi.enums;

public enum USER_OPERATION_TYPE_ENUM {

	CREATE(1, "Insert Operation"),
	UPDATE(2, "Update Operation"),
	DELETE(3, "Delete Operation"),
	GET(4, "Get Operation"),
	LIST(5, "List Operation"),
	CHECKUSEREXIST(6, "Check Operation"),
	CHEKPASSWORD(7, "Check Operation"),
	CHECKPHONENUMBERISBLOCKED(8, "Check Operation"),
	LOGIN(9, "Other Operation");
	
	private final int code;
	private final String message;
	
	USER_OPERATION_TYPE_ENUM(int code, String message) { this.code = code; this.message = message; }
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
