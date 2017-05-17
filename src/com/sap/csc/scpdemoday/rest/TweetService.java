package com.sap.csc.scpdemoday.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.csc.scpdemoday.controller.TwitterController;
import com.sap.csc.scpdemoday.model.Tweet;

@RestController
@RequestMapping("/Tweets")
public class TweetService {

	@Autowired
	private TwitterController twitterController;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<Tweet> getAll() {
		return twitterController.getAllTweets();
	}
}