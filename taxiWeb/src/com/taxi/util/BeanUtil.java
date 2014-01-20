package com.taxi.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BeanUtil {

	private static BeanUtil instance = null;
	private InitialContext context;
	
	private BeanUtil()
	{
		try {
			this.context = new InitialContext(this.getContextProperties());
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	
	public synchronized static BeanUtil instance(){
		if(instance==null)
			instance = new BeanUtil();
		return instance;
	}
	
	private Properties getContextProperties(){
		
		Properties p = new Properties();
	    p.put(Context.INITIAL_CONTEXT_FACTORY,
	        "org.jnp.interfaces.NamingContextFactory");
	    p.put(Context.URL_PKG_PREFIXES,
	        "org.jboss.naming:org.jnp.interfaces");
	    
	    p.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1099");
	    return p;
	}
	
	
	public Object getBean(String bean) {
		Object retVal = null;
		try {
			retVal = this.context.lookup(bean+"/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
}
