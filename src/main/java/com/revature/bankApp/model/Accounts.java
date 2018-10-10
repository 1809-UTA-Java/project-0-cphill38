package com.revature.bankApp.model;


import com.revature.bankApp.repository.AccountsDao;

public class Accounts {

	private int accountID;
	private double balance;

	public Accounts(int accountID, double balance) {
		super();
		this.accountID = accountID;
		this.balance = balance;
	}

	public Accounts() {
		super();
	}

	public static void deposit(int accountNum, double amountToDeposit) {

		AccountsDao adao = new AccountsDao();

		if (amountToDeposit < 0) {
			System.out.println("Ammount to deposit should be greater than $0.00.");
		} else {
			double balance = adao.getBalance(accountNum) + amountToDeposit;
			adao.updateBalance(accountNum, balance);
			System.out.println("Deposit Successful: Available balance is $" + adao.getBalance(accountNum));
		}
	}

	public static boolean withdraw(int accountNum, double amountToWithdraw) {
		AccountsDao adao = new AccountsDao();

		if (amountToWithdraw < 0) {
			System.out.println("Ammount to withdraw should be a greater than $0.00.");
			return false;
		} else if (adao.getBalance(accountNum) - amountToWithdraw < 0) {
			System.out.println("Cannot withdraw that amount. Available balance is $" + adao.getBalance(accountNum));
			return false;
		} else {
			double balance = adao.getBalance(accountNum) - amountToWithdraw;
			adao.updateBalance(accountNum, balance);
			System.out.println("Withdraw successful: Available balance is $" + adao.getBalance(accountNum));
			return true;
		}

	}

	public static void transferFunds(int accountOut, int accountIn, double amount) {
		boolean success = withdraw(accountOut, amount);
		if (success)
			deposit(accountIn, amount);

	}

	@Override
	public String toString() {
		return "Accounts [accountID=" + accountID + ", balance=" + balance + "]";
	}

}