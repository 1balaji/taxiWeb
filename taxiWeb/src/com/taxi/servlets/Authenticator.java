package com.taxi.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.taxi.descriptor.OperationDescriptor;
import com.taxi.ejbs.IUserBean;
import com.taxi.enums.BEAN_ENUM;
import com.taxi.enums.PROVIDER_ENUM;
import com.taxi.factory.BeanFactory;
import com.taxi.logging.Logger;
import com.taxi.pojos.UserPojo;
import com.taxi.util.HttpUtil;

/**
 * Servlet implementation class Authenticator
 */
@WebServlet("/Authenticator")
public class Authenticator extends GenericServlet {

	private static final long serialVersionUID = 1L;
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_REGDATA_ObjId = "objId";
	private static final String KEY_REGDATA_NAME = "name";
	private static final String KEY_REGDATA_SURNAME = "surname";
	private static final String KEY_REGDATA_EMAIL = "email";
	private static final String KEY_REGDATA_MOBILE = "mobile";
	private static final String KEY_REGDATA_NOTE = "note";
	private static final String KEY_REGDATA_LANGUAGE = "language";
	private static final String KEY_REGDATA_PROVIDERID = "providerId";
	private static final String KEY_REGDATA_PROVIDERUSERID = "providerUserId";
	private static final String ATTR_LOGINWITHPROVIDER = "loginWithProvider";

	/**
	 * @see GenericServlet#GenericServlet()
	 */
	public Authenticator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		IUserBean bean = null;

		try {

			bean = (IUserBean) BeanFactory.instance().getBean(
					BEAN_ENUM.USER_BEAN.getBeanName());

		} catch (NamingException e) {

			Logger.logError(String.format(StrConstants.ERR_BEAN_LOOKUP,
					e.getMessage()));

		}

		if (bean != null) {

			try {

				String json = HttpUtil.getPostData(request);
				OperationDescriptor descriptor = null;

				JSONObject jsonObj = new JSONObject(json);
				JSONObject attr = jsonObj
						.getJSONObject(StrConstants.API_JSON_KEY_ATTR);

				boolean loginWithProvider = (boolean) attr
						.get(ATTR_LOGINWITHPROVIDER);

				if (!loginWithProvider) {
					JSONObject data = jsonObj
							.getJSONObject(StrConstants.API_JSON_KEY_DATA);

					String username = data.get(KEY_USERNAME) != null ? data
							.getString(KEY_USERNAME) : null;
					String password = data.get(KEY_PASSWORD) != null ? data
							.getString(KEY_PASSWORD) : null;

					descriptor = bean.login(username, password);
				} else

				{

					UserPojo user = new UserPojo();

					JSONObject data = jsonObj
							.getJSONObject(StrConstants.API_JSON_KEY_DATA);

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

				JSONObject responseValue = new JSONObject();
				responseValue.put(StrConstants.API_JSON_KEY_SUCCESS,
						descriptor.isSuccess());
				responseValue.put(StrConstants.API_JSON_KEY_DATA,
						descriptor.getMessage());

				out.println(responseValue.toString());

			} catch (JSONException e) {

				Logger.logError(String.format(StrConstants.ERR_PARSING_JSON,
						e.getMessage()));
				out.println(String.format(StrConstants.ERR_PARSING_JSON,
						e.getMessage()));

			} catch (Exception e) {

				e.printStackTrace();
				out.println(String.format(StrConstants.ERR_GENERIC,
						e.getMessage()));

			}

		}

	}

}
