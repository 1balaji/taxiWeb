package com.taxi.factory;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BeanFactory {
	private static BeanFactory instance = null;
	private InitialContext context;
	
	private BeanFactory()
	{
		try {
			this.context = new InitialContext(this.getContextProperties());
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	
	public synchronized static BeanFactory instance(){
		if(instance==null)
			instance = new BeanFactory();
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
	
	public Object getBean(String bean) throws NamingException {
		return this.context.lookup(bean+"/remote");
	}
	
}
