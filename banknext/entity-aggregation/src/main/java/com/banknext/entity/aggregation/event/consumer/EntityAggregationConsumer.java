package com.banknext.entity.aggregation.event.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.banknext.entity.aggregation.event.publisher.EntityAggregationTxnPublisher;
import com.banknext.txn.Account;
import com.banknext.txn.Entity;

@Component

public class EntityAggregationConsumer {
		
	Log LOGGER = LogFactory.getLog(EntityAggregationConsumer.class);
	@Autowired
	EntityAggregationTxnPublisher acctTxnPub;
		
	@KafkaListener(topics = "${new-account-created-topic}")
    public void listen(Entity entity)
    {        
    	LOGGER.info("---- Message consumed - customer : account - "+ entity.getCustomer().toString() + " : "+entity.getAccount().toString());    	
    	processMessage(entity);
    }

	private void processMessage(Entity entity) {		
		
		Account account = entity.getAccount();
		LOGGER.info("---- Processing message - customer : account - "+ entity.getCustomer().toString() + " : "+entity.getAccount().toString());
		createEntityAggregation(entity);
		acctTxnPub.publish(entity);
	}

	private void createEntityAggregation(Entity entity) 
	{
		LOGGER.info("---- Message processed - customer : account - "+ entity.getCustomer().toString() + " : "+entity.getAccount().toString());	
	}
}