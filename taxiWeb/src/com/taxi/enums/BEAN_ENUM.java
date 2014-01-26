package com.taxi.enums;

public enum BEAN_ENUM {

	USER_BEAN("UserBean");
		
	private final String beanName;
	
	BEAN_ENUM(String beanName) 
	{  
		this.beanName = beanName; 
	}
	
	
	
	public String getBeanName() {
		return this.beanName;
	}
	
}