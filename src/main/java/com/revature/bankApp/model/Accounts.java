package com.revature.bankApp.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//import java.util.Map.Entry;

import com.revature.bankApp.repository.AccountsDao;

public class Accounts implements Serializable {

	private static final long serialVersionUID = 1L;
	private int accountID;
	// private String accountHoldersUsername;
	private double balance;
	private static ArrayList<Accounts> activeAccounts = new ArrayList<>();
	private static String filename = "AccountsList";

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
		if(success)
			deposit(accountIn, amount);

	}


	public static void closeAccountsList() {
		Accounts master = new Accounts();
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(master);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void openActiveAccounts() {
		Accounts master;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			Object obj = ois.readObject();
			master = (Accounts) obj;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Accounts [accountID=" + accountID + ", balance=" + balance + "]";
	}
	
	

}