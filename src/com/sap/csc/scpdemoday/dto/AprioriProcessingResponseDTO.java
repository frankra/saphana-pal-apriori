package com.sap.csc.scpdemoday.dto;

public class AprioriProcessingResponseDTO {

	private int numberOfTweets;
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

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
