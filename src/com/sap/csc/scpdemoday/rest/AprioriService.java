package com.sap.csc.scpdemoday.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.csc.scpdemoday.controller.TwitterController;

import twitter4j.TwitterException;

@RestController
@RequestMapping("/Apriori")
public class AprioriService {
	
	@Autowired
	private TwitterController twitterController;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Object search(@RequestParam String q) {
		
		try {
			return twitterController.processTwitterSearch(q);
		} catch (TwitterException e) {
			e.printStackTrace();
			return e;
		}
	}
}