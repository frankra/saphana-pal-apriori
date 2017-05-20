package com.sap.csc.scpdemoday.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sap.csc.scpdemoday.dto.AprioriResultDTO;
import com.sap.csc.scpdemoday.model.AprioriResult;
import com.sap.csc.scpdemoday.model.Tweet;

@Component
public class TweetDAO {
	@PersistenceContext
	private EntityManager em;

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
	public AprioriResultDTO executeApriori() {
		AprioriResultDTO result = new AprioriResultDTO();
		em.createNativeQuery("DELETE FROM APRIORI_RESULT").executeUpdate();
		em.createNativeQuery("CALL SYSTEM.PAL_APRIORI_RULE(TRANSACTIONS_VIEW, APRIORI_PROCEDURE_CONFIGURATION, APRIORI_RESULT, APRIORI_PMML_MODEL) WITH OVERVIEW").executeUpdate();
		
		result.setRulesCount((Long) em.createQuery("SELECT COUNT(e) FROM AprioriResult e").getSingleResult());
		
		return result;
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
		Query query = em.createQuery("SELECT e FROM AprioriResult e");
		return (Collection<AprioriResult>) query.getResultList();
	}

}
