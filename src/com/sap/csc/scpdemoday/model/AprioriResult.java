package com.sap.csc.scpdemoday.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "APRIORI_RESULT")
public class AprioriResult {
	@Id
	private String prerule;
	@Id
	private String postrule;
	@Column
	private Double support;
	@Column
	private Double confidence;
	@Column
	private Double lift;

	public AprioriResult() {
		super();
	}

	public AprioriResult(String prerule, String postrule, Double support, Double confidence, Double lift) {
		super();
		this.prerule = prerule;
		this.postrule = postrule;
		this.support = support;
		this.confidence = confidence;
		this.lift = lift;
	}

	public String getPrerule() {
		return prerule;
	}

	public void setPrerule(String prerule) {
		this.prerule = prerule;
	}

	public String getPostrule() {
		return postrule;
	}

	public void setPostrule(String postrule) {
		this.postrule = postrule;
	}

	public Double getSupport() {
		return support;
	}

	public void setSupport(Double support) {
		this.support = support;
	}

	public Double getConfidence() {
		return confidence;
	}

	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}

	public Double getLift() {
		return lift;
	}

	public void setLift(Double lift) {
		this.lift = lift;
	}

}
