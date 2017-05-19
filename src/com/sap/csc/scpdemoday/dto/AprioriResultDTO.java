package com.sap.csc.scpdemoday.dto;

public class AprioriResultDTO {
	
	private Long rulesCount;
	
	public AprioriResultDTO() {
		super();
	}

	public AprioriResultDTO(Long rulesCount) {
		super();
		this.rulesCount = rulesCount;
	}

	public Long getRulesCount() {
		return rulesCount;
	}

	public void setRulesCount(Long rulesCount) {
		this.rulesCount = rulesCount;
	}
	
	
}
