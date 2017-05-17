package com.sap.csc.scpdemoday.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TWEET")
public class Tweet {
	
	@Id
	private Long id;
	
	@Column
	private Date date;
	
	@Column
	private String content;
	
	public Tweet(Long id, Date date, String content) {
		super();
		this.id = id;
		this.date = date;
		this.content = content;
	}
	
	public Tweet() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}

