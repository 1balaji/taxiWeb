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
			
			System.out.println(deviceUID);
			System.out.println(deviceModel);
			System.out.println(systemVersion);
			
		} catch (JSONException e) {
			Logger.logError(String.format("Failed to parse json, %s", e.getMessage()));
		}
		
		bean.registerUser(user);
		
	}

}
