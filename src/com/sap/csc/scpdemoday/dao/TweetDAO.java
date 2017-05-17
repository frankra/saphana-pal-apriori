package com.sap.csc.scpdemoday.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sap.csc.scpdemoday.dto.AprioriProcessingResponseDTO;
import com.sap.csc.scpdemoday.model.Tweet;

@Component
public class TweetDAO {
	@PersistenceContext 
	private EntityManager em;
	
	@Transactional
	public void persistTweets(List<Tweet> tweets){
		for (Tweet tweet : tweets){
			em.persist(tweet);
		};
	}
	
	@Transactional
	public void removeAllTweets(){
		try {
			Query deleteAll = em.createNativeQuery("DELETE FROM TWEET");
			deleteAll.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
			//Table might not have been created yet
		}
	}
	
	@Transactional
	public AprioriProcessingResponseDTO executeApriori(){
		AprioriProcessingResponseDTO response = new AprioriProcessingResponseDTO();
		//call procedure
		
		/*Query runApriori = em.createNativeQuery("CALL PAL_APRIORI_RULE(PAL_TRANS_TAB, PAL_CONTROL_TAB, PAL_RESULT_TAB, PAL_PMMLMODEL_TAB) WITH overview;");
		runApriori.executeUpdate();*/
		
		return response;
	}
	
	@Transactional
	public Collection<Tweet> getAll(){
		Query query = em.createQuery("SELECT e FROM Tweet e");
		return (Collection<Tweet>) query.getResultList();
	}
	
}
