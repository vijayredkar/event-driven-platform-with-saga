package com.banknext.entity.aggregation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banknext.txn.Customer;

/*--------------- only for tests -------------------*/


@RestController
@RequestMapping(value = "/account-initiator/")
public class EntityAggregationController {
	/*
	 * @Autowired EntityAggregationPublisher acctTxnPub;
	 * 
	 * @PostMapping(value = "/new") public String producer(@RequestBody Customer
	 * customer) {
	 * 
	 * acctTxnPub.publish(customer);
	 * 
	 * return "Event published"; }
	 */

}