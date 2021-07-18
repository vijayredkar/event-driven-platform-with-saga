package com.banknext.txn;

public class Customer {	
	
	private Integer customerNum;
	private String email;
	private String firstName; 
	private String lastName;
	private String occupation;
	private String citizenship;
	
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
	
	@Override
	public String toString() {
		return "Customer [customerNum=" + customerNum + ",   email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", occupation="
				+ occupation + ", citizenship=" + citizenship + "]";
	}
}
