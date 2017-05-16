package com.sap.csc.scpdemoday.config;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration bean for the standard data source for the application Requires
 * 
 */
@Configuration
public class DataSourceConfig {

	@Bean
	public DataSource dataSource() throws NamingException {
		InitialContext ctx = new InitialContext();
		DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/SCPDemoDay");

		return dataSource;
	}
}
