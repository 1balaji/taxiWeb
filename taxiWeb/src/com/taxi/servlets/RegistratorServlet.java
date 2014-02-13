package com.taxi.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.taxi.util.JsonUtil;
import com.taxi.ejbs.IUserBean;
import com.taxi.enums.BEAN_ENUM;
import com.taxi.factory.*;
import com.taxi.logging.Logger;
import com.taxi.pojos.UserPojo;
import com.taxi.util.HttpUtil;
import com.taxi.util.JsonUtil;

/**
 * Servlet implementation class RegistratorServlet
 */
public class RegistratorServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String KEY_DEVICEUID = "deviceUID";
	private static final String KEY_SYSVERSION = "systemVersion";
	private static final String KEY_DEVICEMODEL = "deviceModel";
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
		
		//IUserBean bean = (IUserBean)BeanFactory.instance().getBean(BEAN_ENUM.USER_BEAN.getBeanName());
		//UserPojo user = new UserPojo();
		
		String json = HttpUtil.getPostData(request);
		
		try {
		
			/*
			
			this.jsonObj = new JSONObject(json);
			
			JSONObject data = this.jsonObj.getJSONObject(StrConstants.API_JSON_KEY_DATA);
			String username = data.getString(KEY_REGDATA_USERNAME);
			String password = data.getString(KEY_REGDATA_PASSWORD);
			String name = data.getString(KEY_REGDATA_NAME);
			String surname = data.getString(KEY_REGDATA_SURNAME);
			String email = data.getString(KEY_REGDATA_EMAIL);
			String mobile = data.getString(KEY_REGDATA_MOBILE);
			String note = data.getString(KEY_REGDATA_NOTE);
			String language = data.getString(KEY_REGDATA_LANGUAGE);
			
			*/
			
			//this.jsonUtil = new JsonUtil(true, null);
			
			JSONObject responseValue = new JSONObject();
			responseValue.put(StrConstants.API_JSON_KEY_SUCCESS, true);
			responseValue.put(StrConstants.API_JSON_KEY_DATA, "Registration completed successfully");
			
			PrintWriter out = response.getWriter();
			out.println(responseValue.toString());
			
			
		} catch (JSONException e) {
			Logger.logError(String.format("Failed to parse json, %s", e.getMessage()));
		}
		
	//	bean.registerUser(user);
		
	}

}
