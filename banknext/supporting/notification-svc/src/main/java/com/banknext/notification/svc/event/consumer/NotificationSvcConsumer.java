package com.banknext.notification.svc.event.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.banknext.txn.Account;
import com.banknext.txn.Entity;


@Component

public class NotificationSvcConsumer {
	
	Log LOGGER = LogFactory.getLog(NotificationSvcConsumer.class);
	
	@Value("${new-entitymap-created-topic}")
	String newEntityMapCreatedTopic;
	
	@Value("${entitymap-failure-topic}")
	String entityMapFailureTopic;
		
	
	
	//@KafkaListener(topics = "${new-entitymap-created-topic , entitymap-failure-topic}")
	//@KafkaListener(topics = {"new_entitymap_created_topic" , "entitymap_failure_topic"})
	@KafkaListener(topics = {"${new-entitymap-created-topic}" , "${entitymap-failure-topic}"})
	
    public void listen(Entity entity)
    {        
    	LOGGER.info("---- Message consumed - New Customer-Account created : "+ entity.getCustomer().toString());    	
    	processMessage(entity);
    }

	private void processMessage(Entity entity) {		
		
		Account account = entity.getAccount();
		LOGGER.info("---- Processing message account : "+ account.toString());
		notify(entity);
	}

	private void notify(Entity entity) {		
		LOGGER.info("---- Notified - Customer and Account final response " + entity.toString());		
	}
}