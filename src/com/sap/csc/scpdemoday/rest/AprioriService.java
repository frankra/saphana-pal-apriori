package com.sap.csc.scpdemoday.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.csc.scpdemoday.controller.TwitterController;
import com.sap.csc.scpdemoday.dto.AprioriResultDTO;
import com.sap.csc.scpdemoday.model.AprioriResult;

import twitter4j.TwitterException;

@RestController
@RequestMapping("/Apriori")
public class AprioriService {

	private int MIN_RESULT = 10;

	@Autowired
	private TwitterController twitterController;

	@RequestMapping(value = "/gatherTweets", method = RequestMethod.GET)
	public Object search(@RequestParam String q) {

		try {
			return twitterController.gatherTweets(q);
		} catch (TwitterException e) {
			e.printStackTrace();
			return e;
		}
	}

	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public AprioriResultDTO run() {
		return twitterController.runApriori();

	}

	@RequestMapping(value = "/getRulesBySupport", method = RequestMethod.GET)
	public Collection<AprioriResult> getRulesBySupport(@RequestParam int count) {
		if (count == 0) {
			count = MIN_RESULT;
		}
		return twitterController.getRulesBySupport(count);
	}

	@RequestMapping(value = "/getRulesByConfidence", method = RequestMethod.GET)
	public Collection<AprioriResult> getRulesByConfidence(@RequestParam int count) {
		if (count == 0) {
			count = MIN_RESULT;
		}
		return twitterController.getRulesByConfidence(count);
	}

	@RequestMapping(value = "/getRulesByLift", method = RequestMethod.GET)
	public Collection<AprioriResult> getRulesByLift(@RequestParam int count) {
		if (count == 0) {
			count = MIN_RESULT;
		}
		return twitterController.getRulesByLift(count);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<AprioriResult> getAll() {
		return twitterController.getAllAprioriResults();
	}
}