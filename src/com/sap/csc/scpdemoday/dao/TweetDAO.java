package com.sap.csc.scpdemoday.dao;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sap.csc.scpdemoday.model.AprioriResult;
import com.sap.csc.scpdemoday.model.Tweet;

@Component
public class TweetDAO {
	@PersistenceContext
	private EntityManager em;

	private String SQL_SRC_PATH = "/com/sap/csc/scpdemoday/sql/";
	
	public TweetDAO(){
		//this.bootstrapSQL();
	}
	@Transactional
	public void persistTweets(List<Tweet> tweets) {
		this.cleanup();
		for (Tweet tweet : tweets) {
			em.persist(tweet);
		}
	}
	@Transactional
	public void cleanup() {
		em.createNativeQuery("CALL SYSTEM.CLEANUP()").executeUpdate();
	}
	@Transactional
	public void executeApriori() {
		em.createNativeQuery("CALL SYSTEM.PAL_APRIORI_RULE(TRANSACTIONS_VIEW, APRIORI_PROCEDURE_CONFIGURATION, APRIORI_RESULT, APRIORI_PMML_MODEL) WITH OVERVIEW").executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Tweet> getAllTweets() {
		Query query = em.createQuery("SELECT e FROM Tweet e");
		return (Collection<Tweet>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<AprioriResult> getRulesBySupport(int limit) {
		Query query = em.createQuery("SELECT e FROM AprioriResult e ORDER BY e.support DESC");
		return (Collection<AprioriResult>) query.setMaxResults(limit).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<AprioriResult> getRulesByConfidence(int limit) {
		Query query = em.createQuery("SELECT e FROM AprioriResult e ORDER BY e.confidence DESC");
		return (Collection<AprioriResult>) query.setMaxResults(limit).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<AprioriResult> getRulesByLift(int limit) {
		Query query = em.createQuery("SELECT e FROM AprioriResult e ORDER BY e.lift DESC");
		return (Collection<AprioriResult>) query.setMaxResults(limit).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<AprioriResult> getAllAprioriResults() {
		Query query = em.createQuery("SELECT e FROM AprioriResult");
		return (Collection<AprioriResult>) query.getResultList();
	}
	@Transactional
	public void bootstrapSQL() {
		try {
			String sqlScript = this.readFile("BOOTSTRAP.sql");
			Query query = em.createNativeQuery(sqlScript);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private String readFile(String filePath) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		try {
		    return IOUtils.toString(this.getClass().getResourceAsStream(SQL_SRC_PATH + filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
