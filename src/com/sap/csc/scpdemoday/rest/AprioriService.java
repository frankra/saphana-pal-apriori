package com.sap.csc.scpdemoday.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Apriori")
public class AprioriService {
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Object search() {
		return null;
	}
}