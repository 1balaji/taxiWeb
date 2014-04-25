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

		OperationDescriptor descriptor;

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

						boolean loginWithProvider = (boolean) attr.get(ATTR_LOGINWITHPROVIDER);

						if (!loginWithProvider) {

							String username = (String) data.get(KEY_USERNAME);
							String password = (String) data.get(KEY_PASSWORD);

							descriptor = bean.login(username, password);
							
							responseValue = returnDefaultMessage(descriptor, operationType);
							
							out.println(responseValue.toString());
							
						} else {

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

							descriptor = bean.loginWithProvider(user);
							
							responseValue = returnDefaultMessage(descriptor, operationType);
							
							out.println(responseValue.toString());
						}

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


}
