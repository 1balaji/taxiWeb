package com.taxi.pojos;

import java.io.Serializable;


public abstract class BasePojo 
{
	

	private static final long serialVersionUID = 1L;
	
	
	public BasePojo()
	{
		
	}
	
	private int operationType;

	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
}
