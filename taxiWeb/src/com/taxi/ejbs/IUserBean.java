package com.taxi.ejbs;

import com.taxi.descriptor.OperationDescriptor;
import com.taxi.pojos.UserPojo;

public interface IUserBean {

		//Select
	public OperationDescriptor checkUserName(String userName);
	public OperationDescriptor userExists(String userName, int providerId, String providerUserId);
	public OperationDescriptor isPhoneNumberBlocked(String phoneNumber);
	
		//end of Select
		
	public OperationDescriptor login(String username, String password);	
	public OperationDescriptor loginWithProvider(UserPojo user);
		//Update
		
		//end of Update
		
		//Insert
	public OperationDescriptor manageUser(UserPojo user);
		//end of Insert
		
		//Delete
		
		//end of Delete
	
}
