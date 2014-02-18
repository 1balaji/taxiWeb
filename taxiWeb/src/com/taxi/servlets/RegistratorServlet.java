package com.taxi.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.taxi.enums.OPERATION_TYPE_ENUM;
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
	private static final String KEY_REGDATA_USERNAME = "username";
	private static final String KEY_REGDATA_PASSWORD = "password";
	private static final String KEY_REGDATA_NAME = "name";
	private static final String KEY_REGDATA_SURNAME = "surname";
	private static final String KEY_REGDATA_EMAIL = "email";
	private static final String KEY_REGDATA_MOBILE = "mobile";
	private static final String KEY_REGDATA_NOTE = "note";
	private static final String KEY_REGDATA_LANGUAGE = "language";
	private static final String KEY_REGDATA_OPERATION_TYPE = "operation";
	
	
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
		
		PrintWriter out = response.getWriter();
		IUserBean bean = null;
		
		try {
			
			bean  = (IUserBean)BeanFactory.instance().getBean(BEAN_ENUM.USER_BEAN.getBeanName());
			
		} catch(NamingException e) {
			
			Logger.logError(String.format(StrConstants.ERR_BEAN_LOOKUP, e.getMessage()));
			
		}
		
		if(bean!=null) {
		
			try {
				
				String json = HttpUtil.getPostData(request);
				UserPojo user = new UserPojo();
				OperationDescriptor descriptor = null;
				
			    JSONObject jsonObj = new JSONObject(json);
				JSONObject data = jsonObj.getJSONObject(StrConstants.API_JSON_KEY_DATA);
				
				String username = data.get(KEY_REGDATA_USERNAME)!=null ? data.getString(KEY_REGDATA_USERNAME) : null;
				String password = data.get(KEY_REGDATA_PASSWORD)!=null ? data.getString(KEY_REGDATA_PASSWORD): null;
				String name 	= data.get(KEY_REGDATA_NAME)!=JSONObject.NULL ? data.getString(KEY_REGDATA_NAME) : null;
				String surname  = data.get(KEY_REGDATA_SURNAME)!=JSONObject.NULL ? data.getString(KEY_REGDATA_SURNAME): null;
				String email    = data.get(KEY_REGDATA_EMAIL)!=JSONObject.NULL ? data.getString(KEY_REGDATA_EMAIL): null;
				String mobile   = data.get(KEY_REGDATA_MOBILE)!=JSONObject.NULL ? data.getString(KEY_REGDATA_MOBILE): null;
				String note     = data.get(KEY_REGDATA_NOTE)!=JSONObject.NULL ? data.getString(KEY_REGDATA_NOTE): null;
				String language = data.get(KEY_REGDATA_LANGUAGE)!=JSONObject.NULL ? data.getString(KEY_REGDATA_LANGUAGE): null;
				
				int operationType = jsonObj.getInt(KEY_REGDATA_OPERATION_TYPE);
				
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
				
				JSONObject responseValue = new JSONObject();
				responseValue.put(StrConstants.API_JSON_KEY_SUCCESS, descriptor.isSuccess());
				responseValue.put(StrConstants.API_JSON_KEY_DATA, descriptor.getMessage());
				
				out.println(responseValue.toString());
				
			} catch (JSONException e) {
				
				Logger.logError(String.format(StrConstants.ERR_PARSING_JSON, e.getMessage()));
				out.println(String.format(StrConstants.ERR_PARSING_JSON, e.getMessage()));
				
			} catch (Exception e) {
				
				e.printStackTrace();
				out.println(String.format(StrConstants.ERR_GENERIC, e.getMessage()));
				
			}
			
		}
		
	}

}
