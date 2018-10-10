package com.revature.bankApp;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.revature.bankApp.model.Accounts;
import com.revature.bankApp.repository.AccountsDao;

/**
 * Unit test for simple App.
 */
public class AccountsTest {

	@Test
    public void withdrawMethodTest() {
    	//Accounts a = new Accounts();
    	
    	boolean expectedResult = false;
    	boolean actualResult = Accounts.withdraw(3847, 400);
    	
    	assertEquals(expectedResult, actualResult);
    }
	
	@Test // Added when balance was at $300.00
    public void withdrawMethodTest2() {
    	//Accounts a = new Accounts();
    	
    	boolean expectedResult = true;
    	boolean actualResult = Accounts.withdraw(3847, 100);
    	
    	assertEquals(expectedResult, actualResult);
    }

	@Test // Ran when database had balance of $575.00 for this account
	public void getBalanceMethodTest() {
		AccountsDao adao = new AccountsDao();
		
		double expectedResult = 575.00;
		double actualResult = adao.getBalance(9753);
		
		assertEquals(expectedResult, actualResult, 0.00);
	}
}
