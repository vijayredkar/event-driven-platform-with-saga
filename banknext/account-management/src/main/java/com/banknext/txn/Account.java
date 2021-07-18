package  com.banknext.txn; 

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account 
{
	private Integer accountNum;
	private String type;
	private BigDecimal dailyWithdrwalLimit; 
	private BigDecimal minBalance;
	private Boolean hni;
	private Boolean overdraft;	
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Boolean enabled;
	
	public Integer getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(Integer accountNum) {
		this.accountNum = accountNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getDailyWithdrwalLimit() {
		return dailyWithdrwalLimit;
	}
	public void setDailyWithdrwalLimit(BigDecimal dailyWithdrwalLimit) {
		this.dailyWithdrwalLimit = dailyWithdrwalLimit;
	}
	public BigDecimal getMinBalance() {
		return minBalance;
	}
	public void setMinBalance(BigDecimal minBalance) {
		this.minBalance = minBalance;
	}
	public Boolean getHni() {
		return hni;
	}
	public void setHni(Boolean hni) {
		this.hni = hni;
	}
	public Boolean getOverdraft() {
		return overdraft;
	}
	public void setOverdraft(Boolean overdraft) {
		this.overdraft = overdraft;
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
		return "Account [accountNum=" + accountNum + ", type=" + type + ", dailyWithdrwalLimit=" + dailyWithdrwalLimit
				+ ", minBalance=" + minBalance + ", hni=" + hni + ", overdraft=" + overdraft + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", enabled=" + enabled + "]";
	}
		
}

	
