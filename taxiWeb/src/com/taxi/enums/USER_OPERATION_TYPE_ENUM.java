package com.taxi.enums;

public enum OPERATION_TYPE_ENUM {

	CREATE(1, "Insert Operation"),
	UPDATE(2, "Update Operation"),
	DELETE(3, "Delete Operation"),
	GET(4, "Get Operation"),
	LIST(5, "List Operation"),
	OTHER(6, "Other Operation");
	
	private final int code;
	private final String message;
	
	OPERATION_TYPE_ENUM(int code, String message) { this.code = code; this.message = message; }
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
