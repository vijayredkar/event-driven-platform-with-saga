package com.banknext.account.mgt.validate;

import com.banknext.account.mgt.enums.AccountTypes;
import com.banknext.txn.Entity;

public class Validator {

	//check if incoming account type in the valid enum list
		public static boolean isValidTxn(Entity entity) 
		{
			try 
			{
				AccountTypes.valueOf(entity.getAccount().getType());
			} catch (Exception e) 
			{
				return false;
			}
			
			return true;
		}
}
