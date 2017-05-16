package com.sap.csc.scpdemoday.config;

import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

	private static final String CONFIG_LOCATION = "com.sap.csc.scpdemoday.config";
	private static final String REST_MAPPING_URL = "/api/*";

	/**
	 * Creates the configuration for the application withouth the need for XML
	 * files
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		WebApplicationContext context = getContext();

		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.setInitParameter("defaultHtmlEscape", "true");

		// Maps the root URL for rest services
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet",
				new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(REST_MAPPING_URL);

		/**
		 * Set the default time zone to UTC. Every date will be stored as utc
		 * and the UI will convert it from UTC to the local time zone. By doing
		 * this we guarantee the application can be deployed anywhere and used
		 * from anywhere and the dates will still be consistent.
		 */
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(CONFIG_LOCATION);
		return context;
	}

}