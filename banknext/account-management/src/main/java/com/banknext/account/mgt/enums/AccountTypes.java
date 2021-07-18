package com.banknext.account.mgt.enums;

/*public enum AccountTypes 
{   	
	//private String accountType;
	
	
	
}*/  




public enum AccountTypes 
{
	SAVINGS("SAVINGS"),
	CURRENT("CURRENT");

	private String accountType;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	AccountTypes(String accountType) 
	{
		this.setAccountType(accountType);
	}

	/*
	 * public String getDayOfWeek() { return dayOfWeek; }
	 * 
	 * public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
	 */
}