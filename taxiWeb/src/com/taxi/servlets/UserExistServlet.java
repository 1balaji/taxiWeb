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
@WebServlet("/UserExistServlet")
public class UserExistServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	private static final String KEY_USERNAME = "username";
	private static final String KEY_REGDATA_PROVIDERID = "providerId";
	private static final String KEY_REGDATA_PROVIDERUSERID = "providerUserId";

	/**
	 * @see GenericServlet#GenericServlet()
	 */
	public UserExistServlet() {
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
				JSONObject data = jsonObj
						.getJSONObject(StrConstants.API_JSON_KEY_DATA);

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

				JSONObject responseValue = new JSONObject();
				JSONObject responseValueData = new JSONObject();
				
				responseValueData.put(StrConstants.API_JSON_KEY_EXIST, descriptor.getSource());
				
				
				responseValue.put(StrConstants.API_JSON_KEY_SUCCESS,
						descriptor.isSuccess());
				responseValue.put(StrConstants.API_JSON_KEY_MESSAGE,
						descriptor.getMessage());
				responseValue.put(StrConstants.API_JSON_KEY_DATA,
						responseValueData.toString());

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
