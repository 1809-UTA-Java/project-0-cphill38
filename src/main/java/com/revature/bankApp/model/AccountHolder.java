package com.revature.bankApp.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.revature.bankApp.repository.CustomerDao;

public class AccountHolder implements Serializable {

	private static final long serialVersionUID = 1L;
	static AccountHolder master;
	private int custId;
	private String fname;
	private String lname;
	private String userName;
	private int accountNum;
	private String password;
	private ArrayList<Integer> jointAccounts = new ArrayList<>();
	static ArrayList<AccountHolder> customers = new ArrayList<>();
	private static String filename = "ActiveCustomers";

	public AccountHolder() {
		super();
	}
	
	public AccountHolder(String fname, String lname, String userName) {
		this.fname = fname;
		this.lname = lname;
		this.userName = userName;
		PendingAccounts.addPendingCustomer(this);
		// System.out.println(this.fname);
	}
	
	public AccountHolder(int id, String name, String username, int accountNum, String password) {
		super();
		this.custId = id;
		this.fname = name;
		this.userName = username;
		this.accountNum = accountNum;
		this.password = password;
	}

	/*private AccountHolder(String fname, String lname, int accountNum) {
		this.fname = fname;
		this.lname = lname;
		this.accountNum = accountNum;
		customers.add(this);
		Accounts newAccount = new Accounts(accountNum, "username");

	}*/

	/*
	 * private AccountHolder() {
	 * System.out.println("AccountHolder List Initialized."); }
	 */

	public static void serialize() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(master);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void init() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			Object obj = ois.readObject();
			master = (AccountHolder) obj;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		System.out.println(customers);
	}

	public static void viewPendingCustomers(AccountHolder cust) {
		System.out.println(cust.fname + " " + cust.lname + " " + cust.userName);
	}

	static void viewActiveCustomers() {
		Iterator<AccountHolder> iterator = customers.iterator();
		while (iterator.hasNext()) {
			AccountHolder cust = iterator.next();
			System.out.println(cust.fname + " " + cust.lname + " " + cust.userName);
		}

		System.out.println(customers.isEmpty());
	}

	public static boolean addNewAccountHolder(AccountHolder cust, String username) {

		if (username.equals(cust.userName)) {
			customers.add(cust);
			return true;
		} else
			return false;
	}

	public static void thingsToDo(String username) {
		//AccountHolder a;

		System.out.println("Inside menu for customers");
		CustomerDao cdao = new CustomerDao();
		List<AccountHolder> customer = cdao.getCustomer(username);
		for(AccountHolder a : customer) {
			System.out.println(a.toString());
			//Accounts.deposit(a.accountNum, 50.00);
			Accounts.transferFunds(a.accountNum, 3847, 150.00);
		}
	
		//Accounts.deposit(100047, 1000);
		//Accounts.withdrawl(100047, 500);
		//Accounts.deposit(100999, 1500);
		//Accounts.transferFunds(100999, 100555, 300);


	}
	
	@Override
	public String toString() {
		return "AccountHolder [custId=" + custId + ", fname=" + fname + ", userName=" + userName + ", accountNum="
				+ accountNum + "]";
	}
	
	

}