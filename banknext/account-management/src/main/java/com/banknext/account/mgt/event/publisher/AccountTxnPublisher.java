package com.banknext.account.mgt.event.publisher;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.banknext.account.mgt.constants.Constants;
import com.banknext.account.mgt.repo.AccountRepository;
import com.banknext.account.mgt.repo.model.Account;
import com.banknext.account.mgt.validate.Validator;
import com.banknext.txn.Entity;

@Service
public class AccountTxnPublisher {
	
	@Autowired
	AccountRepository acctRepo;
	
	@Value("${new-account-created-topic}")
	String newAcctCreatedTopic;
	
	@Value("${account-failure-topic}")
	String accountFailureTopic;
		
	@Autowired
	private KafkaTemplate<String, Entity> acctKafkaTemplate;
	
	Log LOGGER = LogFactory.getLog(AccountTxnPublisher.class);
		
	public void publish(Entity entity) 
	{
		if(Validator.isValidTxn(entity))
		{	//when txn is validated and eligible for further processing 
			Account account = constructAccount(entity);
			acctRepo.save(account);//persist in datastore
			acctKafkaTemplate.send(newAcctCreatedTopic, constructEntity(entity, account));//publish to success Kafka topic
			LOGGER.info("---- Message published - Account created : " +  entity.getAccount().toString());	
		}
		else
		{	//when txn fails validation
			processfailure(entity); 
			
			/*Account account = updateAccount(entity.getAccount());
			//don't persist in datastore
			acctKafkaTemplate.send(accountFailureTopic, constructEntity(entity, account));//publish to failure Kafka topic
			LOGGER.info("---- Message published - Account failure : " +  entity.getAccount().toString());*/			
		}		
    }
	
	public void processfailure(Entity entity) //SAGA to initiate rollback 
	{
		Integer accountNum = entity.getAccount().getAccountNum();
		Account account = new Account();
		if(accountNum!=null)
		{
			account = acctRepo.findById(accountNum).get();//get account entity from DB
			account = updateAccount(account);//soft delete this account - rollback initiated		
			acctRepo.save(account); //save updated account entity to DB
		}		
		
		//acctKafkaTemplate.send(accountFailureTopic, constructEntity(entity, account));//publish to failure Kafka topic
		acctKafkaTemplate.send(accountFailureTopic, entity);//publish to failure Kafka topic
		LOGGER.info("---- SAGA rollback complete - Account : " +  entity.getAccount().toString());
    }

	private Account constructAccount(Entity entity) {
		
		Account account = new Account();
		
		account.setAccountNum(constructUniqueAcctId());
		account.setDailyWithdrwalLimit(entity.getAccount().getDailyWithdrwalLimit());
		account.setHni(entity.getAccount().getHni());
		account.setMinBalance(entity.getAccount().getMinBalance());
		account.setOverdraft(entity.getAccount().getOverdraft());
		account.setType(entity.getAccount().getType());
				
		Timestamp now = Timestamp.from(Instant.now());		
		account.setCreatedDate(now);
		account.setModifiedDate(now);
		account.setEnabled(true);
		
		return account;
	}
	
	private Account updateAccount(Account account) {
		
		Timestamp now = Timestamp.from(Instant.now());		
		account.setModifiedDate(now);
		
		account.setEnabled(false);//soft delete
		account.setTxnRemarks(Constants.TXN_RMK_SAGA_ROLLBACK);
		
		return account;
	}
	
	private Entity constructEntity(Entity entityDTO, Account account) {
		
		if(account.getAccountNum()!=null)
		{
		 entityDTO.getAccount().setAccountNum(account.getAccountNum());	
		}
				
		return entityDTO;
	}

	private Integer constructUniqueAcctId() {
		Random r = new Random(); 
		  return r.ints(Constants.MININDEX, (Constants.MAXINDEX))
				  .findFirst()
				  .getAsInt();
	}
}