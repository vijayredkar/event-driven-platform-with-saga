package com.banknext.account.mgt.event.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.banknext.account.mgt.event.publisher.AccountTxnPublisher;
import com.banknext.txn.Account;
import com.banknext.txn.Entity;


@Component

public class AccountMgtConsumer {
		
	Log LOGGER = LogFactory.getLog(AccountMgtConsumer.class);
	@Autowired
	AccountTxnPublisher acctTxnPub;
	
	/*
	 * @KafkaListener(topics = "${new-customer-created-topic}") public void
	 * listen(Customer customer) {
	 * LOGGER.info("---- Message consumed - Customer created : "+
	 * customer.toString()); }
	 */
	
	//@KafkaListener(topics = "${new-customer-created-topic}")
	
	/*
		listens to 
		1- success customer created topic    - expected flow
		2- failure entityagrregation topic   - SAGA
	 */
	//@KafkaListener(topics = {"${new-customer-created-topic}" , "${entitymap-failure-topic}"})
	@KafkaListener(topics = "${new-customer-created-topic}")
    public void listenForSuccess(Entity entity)// expected flow
    {        
    	LOGGER.info("---- Message consumed - Customer created : "+ entity.getCustomer().toString());    	
    	processMessage(entity);
    }
	
	@KafkaListener(topics = "${entitymap-failure-topic}")
    public void listenForFailure(Entity entity) // failure capture - SAGA
    {        
		
		LOGGER.info("---- Message consumed - failure received - start rollback SAGA : "+ entity.toString());
    	
    	processMessageFailure(entity);
    }

	private void processMessage(Entity entity) {		
		
		Account account = entity.getAccount();
		LOGGER.info("---- Processing message account : "+ account.toString());
		createAccountEntity(account);
		acctTxnPub.publish(entity);
	}
	
	private void processMessageFailure(Entity entityDTO) {		
		
		LOGGER.info("---- Processing message customer : "+ entityDTO.getCustomer().toString());
		acctTxnPub.processfailure(entityDTO);
	}

	private void createAccountEntity(Account account) 
	{
		LOGGER.info("---- Message processed - New account created : "+ account.toString());	
	}
}