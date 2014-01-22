package com.taxi.servlets.filter;

import java.io.IOException;

import com.taxi.logging.Logger;
import com.taxi.servlets.HTTP_STATUS_CODES;
import com.taxi.servlets.StrConstants;
import com.taxi.servlets.filter.GenericFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContentTypeFilter extends GenericFilter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse resp = (HttpServletResponse)response;
	    this.out = resp.getWriter();
	    
	    if(req.getContentType()!=null && req.getContentType().equals(StrConstants.CONTENT_TYPE_APP_JSON)) {
	    	super.doFilter(request, response, chain);
	    } else {
	    	
	    	resp.setStatus(HTTP_STATUS_CODES.INVALID_CONTENT_TYPE.getCode());
	    	Logger.logError("Irakli : " + HTTP_STATUS_CODES.INVALID_CONTENT_TYPE.getMessage());
	    	
	    	this.out.println(HTTP_STATUS_CODES.INVALID_CONTENT_TYPE.getMessage());
	    }
	    
	    
	}
	
}