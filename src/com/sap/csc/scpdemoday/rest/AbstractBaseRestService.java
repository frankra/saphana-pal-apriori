package com.sap.csc.scpdemoday.rest;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonView;
import com.sap.csc.scpdemoday.controller.AbstractModelOperator;
import com.sap.csc.scpdemoday.controller.AbstractModelQuery;
import com.sap.csc.scpdemoday.model.AbstractModel;
import com.sap.csc.scpdemoday.model.View;
import com.sap.csc.scpdemoday.repository.AbstractModelRepository;

public abstract class AbstractBaseRestService<ID extends Serializable, T extends AbstractModel<ID>, O extends AbstractModelOperator<ID, T, ? extends AbstractModelRepository<ID, T>>, Q extends AbstractModelQuery<ID, T, ?>> {

	@Autowired
	private Q query;

	@Autowired
	private O operator;

	public AbstractBaseRestService(O operator, Q query) {
		this.query = query;
	}

	@RequestMapping(method = RequestMethod.POST)
	public T create(@RequestBody T instance) {
		return getOperator().create(instance);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public T update(@PathVariable ID id, @RequestBody T instance) {
		return getOperator().update(id, instance);
	}

	@JsonView(View.SummaryList.class)
	@RequestMapping(method = RequestMethod.GET)
	public List<T> findAll() {
		return getQuery().findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public T findOne(@PathVariable ID id) {
		return getQuery().findOne(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable ID id) {
		getOperator().delete(id);
		return true;
	}

	public Q getQuery() {
		return query;
	}

	public O getOperator() {
		return operator;
	}
	


}
