package com.taxi.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.taxi.logging.Logger;

public class HttpUtil {

	public static String getPostData(HttpServletRequest req) {
	    
		StringBuilder sb = new StringBuilder();
	    
		try {

	    	BufferedReader reader = req.getReader();
	        reader.mark(10000);
	        String line = null;
	        
	        do {
	            line = reader.readLine();
	            sb.append(line);
	        } while (line != null);
	    
	        reader.reset();
	    
	    } catch(IOException e) {
	        Logger.logError(String.format("Failed to retrieve post data, %s",  e.getMessage()));
	    }

	    return sb.toString();
	}
	
}
