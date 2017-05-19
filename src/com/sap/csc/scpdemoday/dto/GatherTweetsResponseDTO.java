package com.sap.csc.scpdemoday.dto;

public class GatherTweetsResponseDTO {

	private int numberOfTweets;
	private String query;

	public GatherTweetsResponseDTO() {
		super();
	}

	public GatherTweetsResponseDTO(int numberOfTweets) {
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
