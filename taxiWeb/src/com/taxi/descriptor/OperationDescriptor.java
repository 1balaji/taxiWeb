package com.taxi.descriptor;

import java.io.Serializable;

public class OperationDescriptor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isSuccess;
	private int 	code;
	private String 	message;
	private Object  source;
	
	public OperationDescriptor() {
		
	}
	
	public OperationDescriptor(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
	
	public OperationDescriptor(boolean isSuccess, int code, String message, Object source) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
		this.source = source;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getSource() {
		return source;
	}
	public void setSource(Object source) {
		this.source = source;
	}
	
	
}
