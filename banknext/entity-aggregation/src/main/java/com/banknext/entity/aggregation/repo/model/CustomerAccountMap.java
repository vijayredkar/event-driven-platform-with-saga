package com.banknext.entity.aggregation.repo.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_account_map")
public class CustomerAccountMap 
{
	@Id
	private Integer accountCustomerMapId;
	private Integer customerNum;
	private Integer accountNum;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Boolean enabled;
	
	public Integer getAccountCustomerMapId() {
		return accountCustomerMapId;
	}
	public void setAccountCustomerMapId(Integer accountCustomerMapId) {
		this.accountCustomerMapId = accountCustomerMapId;
	}
	public Integer getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(Integer customerNum) {
		this.customerNum = customerNum;
	}
	public Integer getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(Integer accountNum) {
		this.accountNum = accountNum;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "AccountCustomerMap [accountCustomerMapId=" + accountCustomerMapId + ", customerNum=" + customerNum
				+ ", accountNum=" + accountNum + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", enabled=" + enabled + "]";
	}
		
}

	
