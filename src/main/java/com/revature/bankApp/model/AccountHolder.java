package com.revature.bankApp.model;


import java.util.List;
import java.util.Scanner;

import com.revature.bankApp.repository.*;

public class AccountHolder {

	private int custId;
	private String name;
	private String username;
	private int accountNum;
	private String password;

	public AccountHolder() {
		super();
	}

	public AccountHolder(String name, String username, String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public AccountHolder(int id, String name, String username, int accountNum, String password) {
		super();
		this.custId = id;
		this.name = name;
		this.username = username;
		this.accountNum = accountNum;
		this.password = password;
	}

	public static void thingsToDo(String username) {

		Scanner sc = new Scanner(System.in);
		CustomerDao cdao = new CustomerDao();
		int option;
		boolean logout = false;

		System.out.println("\n-----Customer Menu-----");

		List<AccountHolder> customer = cdao.getCustomer(username);
		for (AccountHolder c : customer) {
			while (!logout) {
				System.out.println("\nPlease select an input option below.\n");
				System.out.println("Withdraw Funds (1)\nDeposit Funds (2)\nTransfer Funds (3)"
						+ "\nApply for a joint account (4)\nView Your Account Balance (5)");
				System.out.println("\nLogout (0)");

				option = sc.nextInt();
				switch (option) {
				case 1:
					c.withdraw(c, sc);
					break;
				case 2:
					c.deposit(c, sc);
					break;
				case 3:
					c.transferFunds(c, sc);
					break;
				case 4:
					c.addJointAccount(c, sc);
					break;
				case 5:
					c.viewBalance(c);
					break;
				case 0:
					logout = true;
					break;
				default:
					System.out.println("Invalid option");

				}
			}
		}

		sc.close();
	}

	@Override
	public String toString() {
		return "AccountHolder [custId=" + custId + ", name=" + name + ", username=" + username + ", accountNum="
				+ accountNum + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private void withdraw(AccountHolder c, Scanner sc) {
		System.out.print("How much would you like to withdraw: $");
		double amount = sc.nextDouble();
		Accounts.withdraw(c.accountNum, amount);
	}

	private void deposit(AccountHolder c, Scanner sc) {
		System.out.print("How much would you like to deposit: $");
		double amount = sc.nextDouble();
		Accounts.deposit(c.accountNum, amount);
	}

	private void transferFunds(AccountHolder c, Scanner sc) {
		System.out.print("How much would you like to transfer: $");
		double amount = sc.nextDouble();
		System.out.print("Which account would you like to transfer to:");
		int transferIn = sc.nextInt();
		Accounts.transferFunds(c.accountNum, transferIn, amount);

	}
	
	private void addJointAccount(AccountHolder c, Scanner sc) {
		PendingAccountsDao pdao = new PendingAccountsDao();
		
		System.out.print("Enter Account Number you would like to add:");
		int joint = sc.nextInt();
		pdao.addPendingAccount(c.username, c.password, c.name, c.accountNum, joint);
		
	}
	
	private void viewBalance(AccountHolder c) {
		AccountsDao adao = new AccountsDao();
		double balance = adao.getBalance(c.accountNum);
		System.out.println("Primary Account Balance: $" + balance);
	}

}