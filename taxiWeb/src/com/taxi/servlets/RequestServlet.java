package com.taxi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.taxi.descriptor.OperationDescriptor;
import com.taxi.ejbs.IRequestBean;
import com.taxi.enums.BEAN_ENUM;
import com.taxi.enums.REQUEST_OPERATION_TYPE_ENUM;
import com.taxi.factory.BeanFactory;
import com.taxi.logging.Logger;
import com.taxi.pojos.LocalChargePojo;
import com.taxi.pojos.UserPojo;
import com.taxi.util.HttpUtil;
import com.taxi.util.JsonUtil;

public class RequestServlet  extends GenericServlet {
	
	private static final long serialVersionUID = 1L;

	
	private static final String KEY_COUNTRY = "country";
	private static final String KEY_DISTANCE = "distance";

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestServlet() {
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
		IRequestBean bean = null;

		OperationDescriptor descriptor = null;

		JSONObject responseValue = new JSONObject();
		
		try {

			bean = (IRequestBean) BeanFactory.instance().getBean(BEAN_ENUM.REQUEST_BEAN.getBeanName());

			if (bean != null) {


				String json = HttpUtil.getPostData(request);


				JSONObject jsonObj = new JSONObject(json);

				int operationType = jsonObj.getInt(StrConstants.OPERATION_TYPE);

				Map<String, Object> data = JsonUtil.getMapFromJsonArray(jsonObj.getJSONObject(StrConstants.API_JSON_KEY_DATA));
			//	Map<String, Object> attr = JsonUtil.getMapFromJsonArray(jsonObj.getJSONObject(StrConstants.API_JSON_KEY_ATTR));

				
				if(operationType== REQUEST_OPERATION_TYPE_ENUM.REUESTPAYMENTAMOUNT.getCode())
				{
					
					String country = (String)data.get(KEY_COUNTRY);
					int distance = (int) data.get(KEY_DISTANCE);
					
					descriptor =  bean.getRequestPaymentAmount(country, distance);
					
					responseValue = returnDefaultMessage(descriptor, operationType);
					
					
					LocalChargePojo localChargePojo = (LocalChargePojo)descriptor.getSource();
					
					JSONObject st = JsonUtil.obj2JSONStr(localChargePojo);
					
					responseValue.put(StrConstants.API_JSON_KEY_SOURCE, st);
					
					out.println(responseValue.toString());
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
			responseValue.put(StrConstants.API_JSON_KEY_MESSAGE, e.getMessage());
			Logger.logError(String.format(StrConstants.ERR_GENERIC, e.getMessage()));

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
