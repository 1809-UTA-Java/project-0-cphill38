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

public class Accounts implements Serializable{


	private static final long serialVersionUID = 1L;
	private int accountID;
	private String accountHoldersUsername;
	private double balance;
	private static ArrayList<Accounts> activeAccounts = new ArrayList<>();
	private static String filename = "AccountsList";

	public Accounts(int accountID, String username) {
		this.accountID = accountID;
		this.accountHoldersUsername = username;
		activeAccounts.add(this);
	}
	
	public Accounts() {
		//Creating a master object to serialize.
	}

	private static Accounts locateAccount(int usersID) {
		Accounts obj = null;
		Iterator<Accounts> iterator = activeAccounts.iterator();
		while (iterator.hasNext()) {
			obj = iterator.next();
			if (iterator.next().accountID == usersID) {
				break;
			}
		}
		return obj;
	}

	public static void deposit(int usersID, double amountToDeposit) {
		Accounts accountToDeposit;
		accountToDeposit = locateAccount(usersID);
		accountToDeposit.balance += amountToDeposit;
		System.out.println("Deposit Successful: Available balance is $" + accountToDeposit.balance);

	}

	public static void withdrawl(int usersID, double amountToWithdrawl) {
		Accounts accountToWithdrawl;
		accountToWithdrawl = locateAccount(usersID);
		if (amountToWithdrawl < 0) {
			System.out.println("Ammount to wiithdrawl should be a positive number.");
		} else if (accountToWithdrawl.balance - amountToWithdrawl < 0) {
			System.out.println("Cannot withdrawl that amount. Available balance is $" + accountToWithdrawl.balance);
		} else {
			accountToWithdrawl.balance -= amountToWithdrawl;
			System.out.println("Withdrawl successful: Available balance is $" + accountToWithdrawl.balance);
		}

	}

	public static void transferFunds(int transferOut, int transferIn, double amount) {
		Accounts account;
		account = locateAccount(transferOut);
		boolean success = transferOut(account, amount);
		if (success == true) {
			account = locateAccount(transferIn);
			transferIn(account, amount);
		} else if (success == false)
			System.out.println("Transfer unsuccessful.");
		

	}

	private static boolean transferOut(Accounts account, double amount) {
		if (amount < 0) {
			System.out.println("Ammount to transfer should be a positive number.");
			return false;
		} else if (account.balance - amount < 0) {
			System.out.println("Cannot transfer that amount. Available balance is $" + account.balance);
			return false;
		} else {
			account.balance -= amount;
			System.out.println("Available balance is $" + account.balance);
			return true;
		}
	}

	private static void transferIn(Accounts account, double amount) {
		account.balance += amount;
		System.out.println("Available balance for account " + account + " is $" + account.balance);

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

}