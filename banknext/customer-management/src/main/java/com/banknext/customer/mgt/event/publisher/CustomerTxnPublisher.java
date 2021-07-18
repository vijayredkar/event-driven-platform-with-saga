package com.banknext.customer.mgt.event.publisher;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.banknext.customer.mgt.constants.Constants;
import com.banknext.customer.mgt.repo.CustomerRepository;
import com.banknext.customer.mgt.repo.model.Customer;
import com.banknext.txn.Entity;

@Service
public class CustomerTxnPublisher {
	
	@Autowired
	CustomerRepository custRepo;
	
	@Value("${new-customer-created-topic}")
	String custTxnTopic;
	
	@Autowired
	private KafkaTemplate<String, Entity> customerKafkaTemplate;
	
	Log LOGGER = LogFactory.getLog(CustomerTxnPublisher.class);
		
	public void publish(Entity entity) 
	{
		Customer customer = constructCustomer(entity);
		//custRepo.save(constructCustomer(entity));
		custRepo.save(customer);
		//customerKafkaTemplate.send(custTxnTopic, entity);
		customerKafkaTemplate.send(custTxnTopic, constructEntity(entity, customer));
		LOGGER.info("---- Message published - Customer created : " +  entity.getCustomer().toString());
    }
	
	public void processfailure(Entity entity) //SAGA to initiate rollback 
	{
		Integer customerNum = entity.getCustomer().getCustomerNum();		
		Customer customer = custRepo.findById(customerNum).get();//get customer entity from DB
		
		customer = updateCustomer(customer);//soft delete this customer - rollback initiated		
		custRepo.save(customer); //save updated customer entity to DB
		
		//-- publish - not applicable here
		LOGGER.info("---- SAGA rollback complete - Customer : " +  entity.getCustomer().toString());
    }

	private Customer constructCustomer(Entity entity) {
		
		Customer customer = new Customer();
		
		customer.setCustomerNum(constructUniqueCustId());
		customer.setCitizenship(entity.getCustomer().getCitizenship());
		customer.setEmail(entity.getCustomer().getEmail());
		customer.setFirstName(entity.getCustomer().getFirstName());
		customer.setLastName(entity.getCustomer().getLastName());
		customer.setOccupation(entity.getCustomer().getOccupation());
		customer.setCitizenship(entity.getCustomer().getCitizenship());		
		
		Timestamp now = Timestamp.from(Instant.now());
		
		customer.setCreatedDate(now);
		customer.setModifiedDate(now);
		customer.setEnabled(true);
		
		return customer;
	}
	
	private Customer updateCustomer(Customer customer) {
		
		Timestamp now = Timestamp.from(Instant.now());		
		customer.setModifiedDate(now);
		
		customer.setEnabled(false);//soft delete
		customer.setTxnRemarks(Constants.TXN_RMK_SAGA_ROLLBACK);
		
		return customer;
	}
	
	
	private Entity constructEntity(Entity entityDTO, Customer customer) {
		
		entityDTO.getCustomer().setCustomerNum(customer.getCustomerNum());		
		return entityDTO;
	}

	private Integer constructUniqueCustId() {
		Random r = new Random(); 
		  return r.ints(Constants.minIndex, (Constants.maxIndex))
				  .findFirst()
				  .getAsInt();
	}
}