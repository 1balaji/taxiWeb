package com.taxi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.modelmbean.DescriptorSupport;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.spi.ErrorCode;
import org.json.JSONException;
import org.json.JSONObject;



import com.taxi.descriptor.OperationDescriptor;
import com.taxi.ejbs.IUserBean;
import com.taxi.enums.BEAN_ENUM;
import com.taxi.enums.USER_OPERATION_TYPE_ENUM;
import com.taxi.enums.PROVIDER_ENUM;
import com.taxi.factory.*;
import com.taxi.logging.Logger;
import com.taxi.pojos.UserPojo;
import com.taxi.util.HttpUtil;
import com.taxi.util.JsonUtil;

/**
 * Servlet implementation class RegistratorServlet
 */
public class UserServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	private static final String KEY_REGDATA_ObjId = "objId";
	private static final String KEY_REGDATA_USERNAME = "username";
	private static final String KEY_REGDATA_PASSWORD = "password";
	private static final String KEY_REGDATA_NAME = "name";
	private static final String KEY_REGDATA_SURNAME = "surname";
	private static final String KEY_REGDATA_EMAIL = "email";
	private static final String KEY_REGDATA_MOBILE = "mobile";
	private static final String KEY_REGDATA_NOTE = "note";

	private static final String KEY_REGDATA_LANGUAGE = "language";
	private static final String KEY_REGDATA_PROVIDERID = "providerId";
	private static final String KEY_REGDATA_PROVIDERUSERID = "providerUserId";
	private static final String ATTR_LOGINWITHPROVIDER = "loginWithProvider";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_VERIFICATION_CODE = "verificationCode";
	private static final String KEY_IS_CONFIRMED = "isConfirmed";
	
	private static final String PASSWORD_PATTERN = "^[_A-Za-z0-9!@#$&_]$";

	private Pattern pattern;
	private Matcher matcher;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		IUserBean bean = null;

		OperationDescriptor descriptor = null;

		JSONObject responseValue = new JSONObject();
		
		try {

			bean = (IUserBean) BeanFactory.instance().getBean(BEAN_ENUM.USER_BEAN.getBeanName());

			if (bean != null) {


				String json = HttpUtil.getPostData(request);


				JSONObject jsonObj = new JSONObject(json);

				int operationType = jsonObj.getInt(StrConstants.OPERATION_TYPE);

				Map<String, Object> data = JsonUtil.getMapFromJsonArray(jsonObj.getJSONObject(StrConstants.API_JSON_KEY_DATA));
				Map<String, Object> attr = JsonUtil.getMapFromJsonArray(jsonObj.getJSONObject(StrConstants.API_JSON_KEY_ATTR));

				if (operationType == USER_OPERATION_TYPE_ENUM.CREATE.getCode() 
						|| operationType == USER_OPERATION_TYPE_ENUM.UPDATE.getCode()) {

					int objId = (int) data.get(KEY_REGDATA_ObjId);
					String username = (String) data.get(KEY_REGDATA_USERNAME);
					String password = (String) data.get(KEY_REGDATA_PASSWORD);
					String name = (String) data.get(KEY_REGDATA_NAME);
					String surname = (String) data.get(KEY_REGDATA_SURNAME);
					String email = (String) data.get(KEY_REGDATA_EMAIL);
					String mobile = (String) data.get(KEY_REGDATA_MOBILE);
					String note = (String) data.get(KEY_REGDATA_NOTE);
					String language = (String) data.get(KEY_REGDATA_LANGUAGE);

					UserPojo user = new UserPojo();

					user.setId(objId);
					user.setUsername(username);
					user.setPassword(password);
					user.setName(name);
					user.setSurname(surname);
					user.setEmail(email);
					user.setMobile(mobile);
					user.setNote(note);
					user.setLanguage(language);
					user.setOperationType(operationType);

					descriptor = bean.manageUser(user);
					
					responseValue = returnDefaultMessage(descriptor, operationType);
					
					out.println(responseValue.toString());

				} else {
					if (operationType == USER_OPERATION_TYPE_ENUM.LOGIN.getCode()) {

						
							UserPojo user = new UserPojo();

							int objId = (int) data.get(KEY_REGDATA_ObjId);
							String username = (String) data.get(KEY_USERNAME);
							String password = (String) data.get(KEY_PASSWORD);
							String name = (String) data.get(KEY_REGDATA_NAME);
							String surname = (String) data.get(KEY_REGDATA_SURNAME);
							String email = (String) data.get(KEY_REGDATA_EMAIL);
							String mobile = (String) data.get(KEY_REGDATA_MOBILE);
							String note = (String) data.get(KEY_REGDATA_NOTE);
							String language = (String) data.get(KEY_REGDATA_LANGUAGE);
							String providerIdString = (String) data.get(KEY_REGDATA_PROVIDERID);
							String providerUserId = (String) data.get(KEY_REGDATA_PROVIDERUSERID);

							int providerId = -1;
							if (PROVIDER_ENUM.FACEBOOK.getMessage().equals(providerIdString))
								providerId = PROVIDER_ENUM.FACEBOOK.getCode();
							else if (PROVIDER_ENUM.GOOGLE.getMessage().equals(providerIdString))
								providerId = PROVIDER_ENUM.GOOGLE.getCode();

							user.setId(objId);
							user.setUsername(username);
							user.setPassword(password);
							user.setName(name);
							user.setSurname(surname);
							user.setEmail(email);
							user.setMobile(mobile);
							user.setNote(note);
							user.setLanguage(language);
							user.setProviderID(providerId);
							user.setProviderUserID(providerUserId);

							descriptor = bean.login(user);
							
							responseValue = returnDefaultMessage(descriptor, operationType);
							
							if(descriptor.getSource() != null)
							{
								UserPojo userPojo = (UserPojo)descriptor.getSource();
								
								JSONObject st = JsonUtil.obj2JSONStr(userPojo);
								
								responseValue.put(StrConstants.API_JSON_KEY_SOURCE, st.toString());
							}
							
							out.println(responseValue.toString());
						

					} else {
						if (operationType == USER_OPERATION_TYPE_ENUM.CHECKUSEREXIST.getCode()) {
							String username = (String) data.get(KEY_USERNAME);
							String providerIdString = (String) data.get(KEY_REGDATA_PROVIDERID);
							String providerUserId = (String) data.get(KEY_REGDATA_PROVIDERUSERID);

							int providerId = -1;
							if (PROVIDER_ENUM.FACEBOOK.getMessage().equals(providerIdString))
								providerId = PROVIDER_ENUM.FACEBOOK.getCode();
							else if (PROVIDER_ENUM.GOOGLE.getMessage().equals(providerIdString))
								providerId = PROVIDER_ENUM.GOOGLE.getCode();

							descriptor = bean.userExists(username, providerId,providerUserId);
							
							responseValue = returnDefaultMessage(descriptor, operationType);
							
							out.println(responseValue.toString());
						}
						else
						{
							if(operationType == USER_OPERATION_TYPE_ENUM.CHEKPASSWORD.getCode())
							{
								
								String password = (String) data.get(KEY_PASSWORD);
								
								pattern = Pattern.compile(PASSWORD_PATTERN);
								
								matcher = pattern.matcher(password);
								
								if(matcher.matches())
								{
									responseValue = returnCustomMessage(true, "Password Correct!", 1100, operationType);
									out.println(responseValue.toString());
								}
								else
								{
									responseValue = returnCustomMessage(false, "Invali Characters!", 1101, operationType);
									out.println(responseValue.toString());
								}
							}
							else
							{
								if(operationType == USER_OPERATION_TYPE_ENUM.CHECKPHONENUMBERISBLOCKED.getCode())
								{
									String phoneNumber = (String) data.get(KEY_REGDATA_MOBILE);

									descriptor = bean.isPhoneNumberBlocked(phoneNumber);
									
									responseValue = returnDefaultMessage(descriptor, operationType);
									
									out.println(responseValue.toString());
								}
								else
								{
									if(operationType == USER_OPERATION_TYPE_ENUM.GET.getCode())
									{
										String userName = (String) data.get(KEY_USERNAME);
										int userId = (int) data.get(KEY_REGDATA_ObjId);
										
										descriptor = bean.getUser(userId, userName);
										
										responseValue = returnDefaultMessage(descriptor, operationType);
										
										if(descriptor.getSource() != null)
										{
											UserPojo userPojo = (UserPojo)descriptor.getSource();
											
											JSONObject st = JsonUtil.obj2JSONStr(userPojo);
											
											responseValue.put(StrConstants.API_JSON_KEY_SOURCE, st.toString());
											
											
										}
										
										out.println(responseValue.toString());
										
									}
									else
									{
										//Case check if code is valid, for user id
										if(operationType == USER_OPERATION_TYPE_ENUM.CHECKVERIFICATIONCODE.getCode())
										{
											int verificationCode = (int) data.get(KEY_VERIFICATION_CODE);
											int userId = (int) data.get(KEY_REGDATA_ObjId);
											
											descriptor = bean.checkCode(userId, verificationCode);
											
											responseValue = returnDefaultMessage(descriptor, operationType);
											
											if(descriptor.getSource() != null)
											{
												UserPojo userPojo = (UserPojo)descriptor.getSource();
												
												JSONObject st = JsonUtil.obj2JSONStr(userPojo);
												
												responseValue.put(StrConstants.API_JSON_KEY_SOURCE, st.toString());
												
												
											}
											
											out.println(responseValue.toString());
										}
										else
										{
											//Case for update user confirmation, changing isConfirmed flag
											if(operationType == USER_OPERATION_TYPE_ENUM.UPDATEUSERVERIFICATION.getCode())
											{
												boolean isConfirmed = (boolean) data.get(KEY_IS_CONFIRMED);
												int userId = (int) data.get(KEY_REGDATA_ObjId);
												
												descriptor = bean.updateUserVerification(userId, isConfirmed);
												
												responseValue = returnDefaultMessage(descriptor, operationType);
												
												if(descriptor.getSource() != null)
												{
													UserPojo userPojo = (UserPojo)descriptor.getSource();
													
													JSONObject st = JsonUtil.obj2JSONStr(userPojo);
													
													responseValue.put(StrConstants.API_JSON_KEY_SOURCE, st.toString());
													
													
												}
												
												out.println(responseValue.toString());
											}
											else
											{
												//Case generation new user verification code, and resend again
												if(operationType == USER_OPERATION_TYPE_ENUM.UPDATEUSERVERIFICATIONCODE.getCode())
												{
													String mobile = (String) data.get(KEY_REGDATA_MOBILE);
													int userId = (int) data.get(KEY_REGDATA_ObjId);
													
													descriptor = bean.generateUserConfirmationCode(userId, mobile);
													
													responseValue = returnDefaultMessage(descriptor, operationType);
													
													if(descriptor.getSource() != null)
													{
														UserPojo userPojo = (UserPojo)descriptor.getSource();
														
														JSONObject st = JsonUtil.obj2JSONStr(userPojo);
														
														responseValue.put(StrConstants.API_JSON_KEY_SOURCE, st.toString());
														
														
													}
													
													out.println(responseValue.toString());
												}
												else
												{
													if(operationType == USER_OPERATION_TYPE_ENUM.CONFIRM.getCode())
													{
														int verificationCode = (int) data.get(KEY_VERIFICATION_CODE);
														int userId = (int) data.get(KEY_REGDATA_ObjId);
														
														descriptor = bean.confirm(userId, verificationCode);
														
														responseValue = returnDefaultMessage(descriptor, operationType);
														
														if(descriptor.getSource() != null)
														{
															UserPojo userPojo = (UserPojo)descriptor.getSource();
															
															JSONObject st = JsonUtil.obj2JSONStr(userPojo);
															
															responseValue.put(StrConstants.API_JSON_KEY_SOURCE, st.toString());
															
															
														}
														
														out.println(responseValue.toString());
													}
													else
													{
														if(operationType == USER_OPERATION_TYPE_ENUM.UPDATEUSERMOBILE.getCode())
														{
															String mobile = (String) data.get(KEY_REGDATA_MOBILE);
															int userId = (int) data.get(KEY_REGDATA_ObjId);
															
															descriptor = bean.updateUserMobile(userId, mobile);
															
															responseValue = returnDefaultMessage(descriptor, operationType);
															
															if(descriptor.getSource() != null)
															{
																UserPojo userPojo = (UserPojo)descriptor.getSource();
																
																JSONObject st = JsonUtil.obj2JSONStr(userPojo);
																
																responseValue.put(StrConstants.API_JSON_KEY_SOURCE, st.toString());
																
																
															}
															
															out.println(responseValue.toString());
														}
													}
												}
											}

										}
									}
								}
							}
						}
					}
				}

			}
		} catch (Exception e) {
			out.println(returnExceptionMessage(e).toString());
		}
	}



private JSONObject returnExceptionMessage(Exception e) {

		JSONObject responseValue = new JSONObject();
		try {
			responseValue.put(StrConstants.API_JSON_KEY_SUCCESS, false);
			responseValue.put(StrConstants.API_JSON_KEY_CODE, 0);
			responseValue
					.put(StrConstants.API_JSON_KEY_MESSAGE, e.getMessage());
			Logger.logError(String.format(StrConstants.ERR_GENERIC,
					e.getMessage()));

		} catch (Exception ex) {

			Logger.logError(String.format(StrConstants.ERR_GENERIC,
					ex.getMessage()));
			ex.printStackTrace();
		}

		return responseValue;
	}


	private JSONObject returnDefaultMessage(OperationDescriptor descriptor, int operationType) {

		JSONObject responseValue = new JSONObject();
		try {
			
			responseValue.put(StrConstants.API_JSON_KEY_SUCCESS, descriptor.isSuccess());
			responseValue.put(StrConstants.API_JSON_KEY_CODE, descriptor.getCode());
			responseValue.put(StrConstants.API_JSON_KEY_MESSAGE, descriptor.getMessage());
			responseValue.put(StrConstants.API_JSON_KEY_OPERATION_TYPE, operationType);

		} catch (Exception ex) {

			Logger.logError(String.format(StrConstants.ERR_GENERIC,
					ex.getMessage()));
			ex.printStackTrace();
		}

		return responseValue;
	}

	
	private JSONObject returnCustomMessage(boolean flag, String message, int code, int operationType) {

		JSONObject responseValue = new JSONObject();
		try {
			
			responseValue.put(StrConstants.API_JSON_KEY_SUCCESS, flag);
			responseValue.put(StrConstants.API_JSON_KEY_CODE, code);
			responseValue.put(StrConstants.API_JSON_KEY_MESSAGE, message);
			responseValue.put(StrConstants.API_JSON_KEY_OPERATION_TYPE, operationType);

		} catch (Exception ex) {

			Logger.logError(String.format(StrConstants.ERR_GENERIC,
					ex.getMessage()));
			ex.printStackTrace();
		}

		return responseValue;
	}

}
