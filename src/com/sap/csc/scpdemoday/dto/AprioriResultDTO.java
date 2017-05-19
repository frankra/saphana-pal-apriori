package com.sap.csc.scpdemoday.dto;

public class AprioriResultDTO {
	
	private int rulesCount;
	
	public AprioriResultDTO() {
		super();
	}

	public AprioriResultDTO(int rulesCount) {
		super();
		this.rulesCount = rulesCount;
	}

	public int getRulesCount() {
		return rulesCount;
	}

	public void setRulesCount(int rulesCount) {
		this.rulesCount = rulesCount;
	}
	
	
}
