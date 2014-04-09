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
import com.taxi.factory.BeanFactory;
import com.taxi.logging.Logger;
import com.taxi.util.HttpUtil;

@WebServlet("/PhoneNumberExistsServlet")
public class PhoneNumberExistServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	private static final String KEY_PHONENUMBER = "phoneNumber";

	/**
	 * @see GenericServlet#GenericServlet()
	 */
	public PhoneNumberExistServlet() {
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

				String phoneNumber = data.get(KEY_PHONENUMBER) != JSONObject.NULL ? data
						.getString(KEY_PHONENUMBER) : null;
						

				

				descriptor = bean.isPhoneNumberBlocked(phoneNumber);

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
