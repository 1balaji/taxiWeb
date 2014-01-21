package com.taxi.util;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class ContenTypetFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	    HttpServletRequest req = (HttpServletRequest) request;

	    if(req.getContentType().equals("application/json"))
	    	req.getRequestDispatcher(req.getRequestURI()).forward(request, response);
	    else
	    {
	    	chain.doFilter(request,response);
	    	
	    }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

   
	
}