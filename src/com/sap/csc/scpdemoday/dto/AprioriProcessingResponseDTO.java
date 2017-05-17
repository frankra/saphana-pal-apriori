package com.sap.csc.scpdemoday.dto;

import java.util.Collection;

import com.sap.csc.scpdemoday.model.AprioriResult;

public class AprioriProcessingResponseDTO {
	
	private int numberOfTweets;
	private Collection<AprioriResult> results;
	private String query;
	
	public AprioriProcessingResponseDTO() {
		super();
	}

	public AprioriProcessingResponseDTO(int numberOfTweets) {
		super();
		this.numberOfTweets = numberOfTweets;
	}

	public int getNumberOfTweets() {
		return numberOfTweets;
	}

	public void setNumberOfTweets(int numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
	}

	public Collection<AprioriResult> getResults() {
		return results;
	}

	public void setResults(Collection<AprioriResult> results) {
		this.results = results;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	
	
}
