package com.taxi.ejbs;

import com.taxi.descriptor.OperationDescriptor;
import com.taxi.pojos.UserPojo;

public interface IUserBean {

	// Get
	public OperationDescriptor getUser(int id, String userName);
	public OperationDescriptor checkCode(int userId, int code);
	// public OperationDescriptor listUser();
	// end of Select

	// Update
	public OperationDescriptor updateUserVerification(int userId, boolean isConfirmed);
	// end of Update

	// Insert
	public OperationDescriptor manageUser(UserPojo user);
	public OperationDescriptor userExists(String userName, int providerId, String providerUserId);
	public OperationDescriptor isPhoneNumberBlocked(String phoneNumber);
	// end of Insert

	// Delete

	// end of Delete

	public OperationDescriptor login(UserPojo user);
}
