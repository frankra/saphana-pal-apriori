package com.sap.csc.scpdemoday.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.csc.scpdemoday.controller.CustomerController;
import com.sap.csc.scpdemoday.controller.CustomerQuery;
import com.sap.csc.scpdemoday.model.Customer;

@RestController
@RequestMapping("/Customers")
public class CustomerServlet extends AbstractBaseRestService<Long, Customer, CustomerController, CustomerQuery> {

	@Autowired
	public CustomerServlet(CustomerController controller, CustomerQuery query) {
		super(controller, query);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Customer findOne(@PathVariable Long id) {
		return getQuery().findOne(id);
	}

	@RequestMapping("/search")
	public List<Customer> search(@RequestParam String param){
		long customerId = 0;
		
		try {
			customerId = Long.parseLong(param);
		}catch (Exception e){
			// die
		}
		return this.getOperator().getRepository().findByIdOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(customerId, param, param);
	}	
}