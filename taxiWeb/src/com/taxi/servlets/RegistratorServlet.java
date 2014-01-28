package com.taxi.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.taxi.ejbs.IUserBean;
import com.taxi.enums.BEAN_ENUM;
import com.taxi.factory.*;
import com.taxi.logging.Logger;
import com.taxi.pojos.UserPojo;
import com.taxi.util.HttpUtil;

/**
 * Servlet implementation class RegistratorServlet
 */
public class RegistratorServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String KEY_DEVICEUID = "deviceUID";
	private static final String KEY_SYSVERSION = "systemVersion";
	private static final String KEY_DEVICEMODEL = "deviceModel";
	private static final String KEY_REGDATA = "registrationData";
	private static final String KEY_REGDATA_USERNAME = "username";
	private static final String KEY_REGDATA_PASSWORD = "password";
	private static final String KEY_REGDATA_NAME = "name";
	private static final String KEY_REGDATA_SURNAME = "surname";
	private static final String KEY_REGDATA_EMAIL = "email";
	private static final String KEY_REGDATA_MOBILE = "mobile";
	private static final String KEY_REGDATA_NOTE = "note";
	private static final String KEY_REGDATA_LANGUAGE = "language";
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistratorServlet() {
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
		
		IUserBean bean = (IUserBean)BeanFactory.instance().getBean(BEAN_ENUM.USER_BEAN.getBeanName());
		UserPojo user = new UserPojo();
		
		String data = HttpUtil.getPostData(request);
		
		try {
		
			this.jsonObj = new JSONObject(data);
			
			String deviceUID = this.jsonObj.getString(KEY_DEVICEUID);
			String deviceModel = this.jsonObj.getString(KEY_DEVICEMODEL);
			String systemVersion = this.jsonObj.getString(KEY_SYSVERSION);
			JSONObject registrationData = this.jsonObj.getJSONObject(KEY_REGDATA);
			String systemUsername = registrationData.getString(KEY_REGDATA_USERNAME);
			String systemPassword = registrationData.getString(KEY_REGDATA_PASSWORD);
			String systemName = registrationData.getString(KEY_REGDATA_NAME);
			String systemSurname = registrationData.getString(KEY_REGDATA_SURNAME);
			String systemEmail = registrationData.getString(KEY_REGDATA_EMAIL);
			String systemMobile = registrationData.getString(KEY_REGDATA_MOBILE);
			String systemNote = registrationData.getString(KEY_REGDATA_NOTE);
			String systemLanguage = registrationData.getString(KEY_REGDATA_LANGUAGE);
			
			
			
			System.out.println(deviceUID);
			System.out.println(deviceModel);
			System.out.println(systemVersion);
			
		
			
		} catch (JSONException e) {
			Logger.logError(String.format("Failed to parse json, %s", e.getMessage()));
		}
		
		bean.registerUser(user);
		
	}

}
