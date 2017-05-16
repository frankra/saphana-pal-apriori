package com.sap.csc.scpdemoday.model;

import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

@Table(name = "TWEET")
public class Tweet {
	
	@Column
	@JsonView(View.Summary.class)
	private String name;

}

