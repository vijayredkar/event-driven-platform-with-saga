package com.banknext.entity.aggregation.event.publisher;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.banknext.entity.aggregation.constants.Constants;
import com.banknext.entity.aggregation.repo.CustomerAccountMapRepository;
import com.banknext.entity.aggregation.repo.model.CustomerAccountMap;
import com.banknext.txn.Account;
import com.banknext.txn.Customer;
import com.banknext.txn.Entity;

@Service
public class EntityAggregationTxnPublisher {
	
	@Autowired
	CustomerAccountMapRepository custAcctRepo;
	
	@Autowired
	private KafkaTemplate<String, Entity> custAcctKafkaTemplate;
	
	@Value("${new-entitymap-created-topic}")
	String newEntityMapCreatedTopic;
	
	@Value("${entitymap-failure-topic}")
	String entityMapFailureTopic;		
		
	
	Log LOGGER = LogFactory.getLog(EntityAggregationTxnPublisher.class);
	
	public void publish(Entity entity) 
	{
		
		if(isValidTxn(entity))
		{
			custAcctRepo.save(constructCustomerAccountMap(entity));
			custAcctKafkaTemplate.send(newEntityMapCreatedTopic, entity);
			LOGGER.info("---- Message published - EntityAggregation created : " +  entity.getCustomer().toString() + " : " +  entity.getAccount().toString());	
		}
		else
		{			
			  custAcctKafkaTemplate.send(entityMapFailureTopic, entity);
			  LOGGER.info("**** Message published - EntityAggregation failed : " +
			  entity.getCustomer().toString() + " : " + entity.getAccount().toString());			 	
		}		
    }	
	
	private boolean isValidTxn(Entity entity) 
	{
		Customer customer  = entity.getCustomer();
		Account account    = entity.getAccount();
		
		//eligibility rule  - if customer ctznship is USA then acct type should be SAVINGS
		if(Constants.CTRY_USA.equals(customer.getCitizenship()) && Constants.ACCT_TYPE_SAVINGS.equals(account.getType()))
		{
			return true;			
		}		
		
		
		return false;
	}

	private CustomerAccountMap constructCustomerAccountMap(Entity entity) {
		
		CustomerAccountMap custAcctMap = new CustomerAccountMap();
		
		
		custAcctMap.setAccountCustomerMapId(constructUniqueCustAcctMapId());
		
		  //custAcctMap.setAccountNum(entity.getAccount().getAccountNum());
		  custAcctMap.setCustomerNum(entity.getCustomer().getCustomerNum());
		  custAcctMap.setAccountNum(entity.getAccount().getAccountNum());		
		
		/*
		 * custAcctMap.setAccountNum(1001); custAcctMap.setCustomerNum(1001);
		 */
				
		Timestamp now = Timestamp.from(Instant.now());		
		custAcctMap.setCreatedDate(now);
		custAcctMap.setModifiedDate(now);
		custAcctMap.setEnabled(true);
		
		return custAcctMap;
	}

	private Integer constructUniqueCustAcctMapId() {
		Random r = new Random(); 
		  return r.ints(Constants.MININDEX, (Constants.MAXINDEX))
				  .findFirst()
				  .getAsInt();
	}	
}