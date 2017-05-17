package com.sap.csc.scpdemoday.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.csc.scpdemoday.dao.TweetDAO;
import com.sap.csc.scpdemoday.dto.AprioriProcessingResponseDTO;
import com.sap.csc.scpdemoday.model.Tweet;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterController {
	@Autowired
	TweetDAO tweetDAO;
	
	public AprioriProcessingResponseDTO processTwitterSearch(String searchText) throws TwitterException {
		
		tweetDAO.removeAllTweets();
		
		QueryResult searchResult = this.searchOnTwitter(searchText);
		List<Tweet> tweets = this.convertTweetsFromStatuses(searchResult.getTweets());
		
		tweetDAO.persistTweets(tweets);
		
		AprioriProcessingResponseDTO response = tweetDAO.executeApriori();
		
		response.setNumberOfTweets(searchResult.getCount());
		
		return response;
	}

	private QueryResult searchOnTwitter(String searchText) throws TwitterException {
		TwitterFactory tf = new TwitterFactory();
		Twitter twitter = tf.getInstance();
		Query query = new Query(searchText);
		query.setCount(100);
		QueryResult result = null;

		result = twitter.search(query);
		
		for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
		}
		return result;
	}
	
	private List<Tweet> convertTweetsFromStatuses(List<Status> statuses){
		List<Tweet> tweets = new ArrayList<Tweet>();
		Tweet newTweet;
		
		for (Status status : statuses){
			newTweet = new Tweet(status.getId(),status.getCreatedAt(), status.getText());
			tweets.add(newTweet);
		}
		
		return tweets;
	}
	
	public Collection<Tweet> getAllTweets(){
		return tweetDAO.getAll();
	}
}
