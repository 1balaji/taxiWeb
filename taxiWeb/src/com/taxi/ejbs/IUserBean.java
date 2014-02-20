package com.taxi.ejbs;

import com.taxi.descriptor.OperationDescriptor;
import com.taxi.pojos.UserPojo;

public interface IUserBean {

		//Select
	
		//end of Select
		
	public OperationDescriptor login(String username, String password);	
		//Update
		
		//end of Update
		
		//Insert
	public OperationDescriptor manageUser(UserPojo user);
		//end of Insert
		
		//Delete
		
		//end of Delete
	
}
