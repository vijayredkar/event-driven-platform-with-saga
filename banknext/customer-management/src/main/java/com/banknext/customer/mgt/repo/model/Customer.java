package com.banknext.customer.mgt.repo.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {	
	
	@Id
	private Integer customerNum;
	private String email;
	private String firstName; 
	private String lastName;
	private String occupation;
	private String citizenship;	
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Boolean enabled;
	private String txnRemarks="";
	
	public Integer getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(Integer customerNum) {
		this.customerNum = customerNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getCitizenship() {
		return citizenship;
	}
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
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
	public String getTxnRemarks() {
		return txnRemarks;
	}
	public void setTxnRemarks(String txnRemarks) {
		this.txnRemarks = txnRemarks;
	}
	@Override
	public String toString() {
		return "Customer [customerNum=" + customerNum + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + ", occupation=" + occupation + ", citizenship=" + citizenship + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", enabled=" + enabled + ", txnRemarks=" + txnRemarks
				+ "]";
	}
	
	
			
}
