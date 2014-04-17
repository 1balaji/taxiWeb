package com.taxi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		IUserBean bean = null;
		
		try {
			
			bean  = (IUserBean)BeanFactory.instance().getBean(BEAN_ENUM.USER_BEAN.getBeanName());
			
		} catch(NamingException e) {
			
			Logger.logError(String.format(StrConstants.ERR_BEAN_LOOKUP, e.getMessage()));
			
		}
		
		if(bean!=null) {
		
			Map<String, Object> dataBody = null;
			JSONObject responseValue = new JSONObject();
			
			try {
				
				String json = HttpUtil.getPostData(request);
				
				OperationDescriptor descriptor = null;
				
			    JSONObject jsonObj = new JSONObject(json);
			    
			    int operationType = jsonObj.getInt(StrConstants.OPERATION_TYPE);
			    
				JSONObject data = jsonObj.getJSONObject(StrConstants.API_JSON_KEY_DATA);
				JSONObject attr = jsonObj.getJSONObject(StrConstants.API_JSON_KEY_ATTR);

				
				if(operationType == USER_OPERATION_TYPE_ENUM.CREATE.getCode() || operationType== USER_OPERATION_TYPE_ENUM.UPDATE.getCode())
				{

					int objId 		= data.get(KEY_REGDATA_ObjId)!=null ? data.getInt(KEY_REGDATA_ObjId) : 0;
					String username = data.get(KEY_REGDATA_USERNAME)!=null ? data.getString(KEY_REGDATA_USERNAME) : null;
					String password = data.get(KEY_REGDATA_PASSWORD)!=null ? data.getString(KEY_REGDATA_PASSWORD): null;
					String name 	= data.get(KEY_REGDATA_NAME)!=JSONObject.NULL ? data.getString(KEY_REGDATA_NAME) : null;
					String surname  = data.get(KEY_REGDATA_SURNAME)!=JSONObject.NULL ? data.getString(KEY_REGDATA_SURNAME): null;
					String email    = data.get(KEY_REGDATA_EMAIL)!=JSONObject.NULL ? data.getString(KEY_REGDATA_EMAIL): null;
					String mobile   = data.get(KEY_REGDATA_MOBILE)!=JSONObject.NULL ? data.getString(KEY_REGDATA_MOBILE): null;
					String note     = data.get(KEY_REGDATA_NOTE)!=JSONObject.NULL ? data.getString(KEY_REGDATA_NOTE): null;
					String language = data.get(KEY_REGDATA_LANGUAGE)!=JSONObject.NULL ? data.getString(KEY_REGDATA_LANGUAGE): null;
					
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
				}
				else
				{
					if(operationType == USER_OPERATION_TYPE_ENUM.LOGIN.getCode())
					{

						boolean loginWithProvider = (boolean) attr.get(ATTR_LOGINWITHPROVIDER);

						if (!loginWithProvider) {

							String username = data.get(KEY_USERNAME) != null ? data.getString(KEY_USERNAME) : null;
							String password = data.get(KEY_PASSWORD) != null ? data.getString(KEY_PASSWORD) : null;

							descriptor = bean.login(username, password);
						} else
							{

							UserPojo user = new UserPojo();


							int objId = data.get(KEY_REGDATA_ObjId) != null ? data
									.getInt(KEY_REGDATA_ObjId) : 0;
							String username = data.get(KEY_USERNAME) != JSONObject.NULL ? data
									.getString(KEY_USERNAME) : null;
							String password = data.get(KEY_PASSWORD) != JSONObject.NULL ? data
									.getString(KEY_PASSWORD) : null;
							String name = data.get(KEY_REGDATA_NAME) != JSONObject.NULL ? data
									.getString(KEY_REGDATA_NAME) : null;
							String surname = data.get(KEY_REGDATA_SURNAME) != JSONObject.NULL ? data
									.getString(KEY_REGDATA_SURNAME) : null;
							String email = data.get(KEY_REGDATA_EMAIL) != JSONObject.NULL ? data
									.getString(KEY_REGDATA_EMAIL) : null;
							String mobile = data.get(KEY_REGDATA_MOBILE) != JSONObject.NULL ? data
									.getString(KEY_REGDATA_MOBILE) : null;
							String note = data.get(KEY_REGDATA_NOTE) != JSONObject.NULL ? data
									.getString(KEY_REGDATA_NOTE) : null;
							String language = data.get(KEY_REGDATA_LANGUAGE) != JSONObject.NULL ? data
									.getString(KEY_REGDATA_LANGUAGE) : null;
							String providerIdString = data.get(KEY_REGDATA_PROVIDERID) != JSONObject.NULL ? data
											.getString(KEY_REGDATA_PROVIDERID) : null;
							String providerUserId = data.get(KEY_REGDATA_PROVIDERUSERID) != JSONObject.NULL ? data
													.getString(KEY_REGDATA_PROVIDERUSERID) : null;


													
							int providerId =-1;
							if(PROVIDER_ENUM.FACEBOOK.getMessage().equals(providerIdString))
								providerId= PROVIDER_ENUM.FACEBOOK.getCode();
							else
								if(PROVIDER_ENUM.GOOGLE.getMessage().equals(providerIdString))
									providerId= PROVIDER_ENUM.GOOGLE.getCode();

							
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

							descriptor = bean.loginWithProvider(user);
						}

					}
					else
					{
						if(operationType == USER_OPERATION_TYPE_ENUM.CHECKUSEREXIST.getCode())
						{
							String username = data.get(KEY_USERNAME) != JSONObject.NULL ? data
									.getString(KEY_USERNAME) : null;
							String providerIdString = data.get(KEY_REGDATA_PROVIDERID) != JSONObject.NULL ? data
									.getString(KEY_REGDATA_PROVIDERID) : null;
							String providerUserId = data.get(KEY_REGDATA_PROVIDERUSERID) != JSONObject.NULL ? data
									.getString(KEY_REGDATA_PROVIDERUSERID) : null;

							int providerId = -1;
							if (PROVIDER_ENUM.FACEBOOK.getMessage()
									.equals(providerIdString))
								providerId = PROVIDER_ENUM.FACEBOOK.getCode();
							else if (PROVIDER_ENUM.GOOGLE.getMessage().equals(
									providerIdString))
								providerId = PROVIDER_ENUM.GOOGLE.getCode();

							descriptor = bean.userExists(username, providerId,
									providerUserId);
							
						}
					}
				}
				
				responseValue.put(StrConstants.API_JSON_KEY_SUCCESS, descriptor.isSuccess());

				dataBody = new HashMap<String, Object>(2); 
				dataBody.put(StrConstants.API_JSON_KEY_MESSAGE, descriptor.getMessage());
				dataBody.put(StrConstants.API_JSON_KEY_CODE, descriptor.getCode());
				responseValue.put(StrConstants.API_JSON_KEY_DATA, dataBody);
				out.println(responseValue.toString());
				
			} catch (JSONException e) {
				
				responseValue = new JSONObject();
				dataBody = new HashMap<String, Object>(1); 
				dataBody.put(StrConstants.API_JSON_KEY_MESSAGE, String.format(StrConstants.ERR_PARSING_JSON, e.getMessage()));
				
				try {
					responseValue.put(StrConstants.API_JSON_KEY_DATA, dataBody);
					responseValue.put(StrConstants.API_JSON_KEY_SUCCESS, false);
					
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
				
				Logger.logError(String.format(StrConstants.ERR_PARSING_JSON, e.getMessage()));
				out.println(responseValue.toString());
				
			} catch (Exception e) {
				
				responseValue = new JSONObject();
				dataBody = new HashMap<String, Object>(1); 
				dataBody.put(StrConstants.API_JSON_KEY_MESSAGE, String.format(StrConstants.ERR_GENERIC, e.getMessage()));
				
				
				Logger.logError(String.format(StrConstants.ERR_GENERIC, e.getMessage()));
				out.println(responseValue.toString());
				
			}
			
		}
		
	}

}
