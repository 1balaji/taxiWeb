package com.taxi.logging;

import java.util.Date;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;

public class Logger {

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(
            Logger.class.getName()); 
	private static Appender appender;
	
	static {
		appender = new ConsoleAppender(new SimpleLayout());
		logger.addAppender(appender); 
	}
	
	public static void logError(String message) {
		logger.error(String.format("Time: %s, message: %s",  new Date().toString(), message));
	}
	
	public static void logInfo(String message) {
		logger.info(String.format("Time: %s, message: %s",  new Date().toString(), message));
	}
}
