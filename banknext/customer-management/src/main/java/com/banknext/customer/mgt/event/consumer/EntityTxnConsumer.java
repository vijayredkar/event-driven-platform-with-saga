package com.banknext.customer.mgt.event.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.banknext.customer.mgt.event.publisher.CustomerTxnPublisher;
import com.banknext.customer.mgt.repo.CustomerRepository;
//import com.banknext.customer.mgt.dto.Customer;
import com.banknext.txn.Customer;
import com.banknext.txn.Entity;


@Component
public class EntityTxnConsumer {
	
	Log LOGGER = LogFactory.getLog(EntityTxnConsumer.class);
	
	@Autowired
	CustomerTxnPublisher custTxnPub;
	
	@Autowired
	CustomerRepository custRepo;
	
	/*
		listens to 
		1- success entity initiated topic    - expected flow 
		2- failure account topic  			 - SAGA 
		3- failure entityagrregation topic   - SAGA
	*/
    //@KafkaListener(topics = {"${new-entity-initiated-topic}" , "${account-failure-topic}", "${entitymap-failure-topic}"})
	@KafkaListener(topics = "${new-entity-initiated-topic}")
    //public void listen(Customer customer) 
    public void listenForSuccess(Entity entity)
    {        
    	LOGGER.info("---- Message consumed - Entity creation initiated : "+ entity.toString());
    	
    	processMessage(entity);
    }
    
    @KafkaListener(topics = {"${account-failure-topic}", "${entitymap-failure-topic}"})
    //public void listen(Customer customer) 
    public void listenForFailure(Entity entity)
    {        
    	LOGGER.info("---- Message consumed - failure received - start rollback SAGA : "+ entity.toString());
    	
    	processMessageFailure(entity);
    }

	private void processMessage(Entity entityDTO) {		
		
		Customer customer = entityDTO.getCustomer();
		LOGGER.info("---- Processing message customer : "+ customer.toString());
		//createCustomerEntity(customer);
		custTxnPub.publish(entityDTO);
	}
	
	private void processMessageFailure(Entity entityDTO) {		
		
		com.banknext.customer.mgt.repo.model.Customer customerFromDB = custRepo.findById(entityDTO.getCustomer().getCustomerNum()).get();
		customerFromDB.setEnabled(false);
		custRepo.save(customerFromDB);
		LOGGER.info("---- Processing message customer - rollback SAGA : "+ entityDTO.getCustomer().toString());
		custTxnPub.processfailure(entityDTO);
	}

	/*
	 * private void createCustomerEntity(Customer customer) {
	 * LOGGER.info("---- Message processed - New customer created : "+
	 * customer.toString()); }
	 */
}