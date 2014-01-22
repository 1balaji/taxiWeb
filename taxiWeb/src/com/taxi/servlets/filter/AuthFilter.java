package com.taxi.servlets.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.taxi.util.SHAUtil;

/**
 * Servlet Filter implementation class AuthFilter
 */
public class AuthFilter extends GenericFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse resp = (HttpServletResponse) response;
		
		StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		try {
			JSONObject jsonObj = new JSONObject(jb.toString());
			String sha = jsonObj.getString("sha");
			String localSha = null;
			try {
				localSha = SHAUtil.getSHA512("irakli");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(sha.equals(localSha)) {
				super.doFilter(request, response, chain);
			} else {
				resp.setStatus(900);
				
				resp.getWriter().println("Anauthorized");
			}
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		
	}

}
