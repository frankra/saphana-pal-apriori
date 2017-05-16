package com.sap.csc.scpdemoday.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for JPA
 *
 */
@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

	@Autowired
	private DataSource dataSource;

	/**
	 * Configures the entity manager for JPA
	 * 
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws SQLException, NamingException {
		DataSource ds = this.dataSource;

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(ds);
		// All entities need to be under this package for JPA to manage them
		em.setPackagesToScan(new String[] { "com.sap.csc.scpdemoday.model" });

		JpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		Properties properties = new Properties();
		properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
		properties.setProperty("eclipselink.ddl-generation",
				"create-or-extend-tables");
		properties.setProperty("eclipselink.weaving", "static");
		properties.setProperty("eclipselink.logging.level", "ALL");

		// Disable caching
		properties.setProperty("eclipselink.cache.shared.default", "false");
		properties.setProperty("eclipselink.query-results-cache", "false");
		properties.setProperty("eclipselink.refresh", "true");

		em.setJpaProperties(properties);

		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}