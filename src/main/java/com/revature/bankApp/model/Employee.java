package com.revature.bankApp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.revature.bankApp.repository.*;

public class Employee {

	private boolean isAdmin = false;
	private String name;
	private int employeeID;
	private String userName;
	private String password;
	private boolean login = true;
	private static ArrayList<Employee> employees = new ArrayList<>();

	
	public Employee() {
		super();
	}
	
	/*public Employee() { // Testing right now for system admin.
		this.name = "System";
		//this.lname = "Admin";
		this.employeeID = 001;
		this.userName = "System";
		this.password = "password";
		this.isAdmin = true;
		employees.add(this);
	}*/

	private Employee(String fname, String lname, int employeeID) {
		this.name = fname;
		//this.lname = lname;
		this.employeeID = employeeID;
		employeeBuilder(this);
	}


	private void employeeBuilder(Employee e) {
		//this.userName = e.name + e.lname;
		this.password = "password";
		this.login = false;

	}

	public boolean isAdmin() {
		return this.isAdmin;
	}

	public static void viewEmployees() {
		Iterator<Employee> i = employees.iterator();
		while (i.hasNext()) {
			getEmployee(i.next());
		}
	}

	public static void getEmployee(Employee e) {
		System.out.println(e.name + " " + e.employeeID);
	}

	public static boolean verifyEmployeeCredentials(String userName, String password) {

		boolean isEmployee = false;
		Iterator<Employee> i = employees.iterator();
		while (i.hasNext()) {
			isEmployee = getCreds(i.next(), userName, password);
			if (isEmployee == true)
				break;
		}

		return isEmployee;
	}

	private static boolean getCreds(Employee e, String userName, String password) {
		if (e.userName.equals(userName) && e.password.equals(password)) {
			e.thingsToDo(e);
			return true;
		} else
			return false;
	}

	public void thingsToDo(Employee e) { // Menu for employees once they log-in.
		Scanner sc = new Scanner(System.in);

		System.out.println("Inside Things to Do for Employees");
		viewCustomers();
		viewAccounts();
		
		//adao.createAccount(2387, 200.00);
		//Approve new customer options
		/*viewPendingAccounts();
		System.out.println("Which pending customer would you like to approve?");
		String username = sc.next();
		approveNewCustomer(username);*/
		

		sc.close();
	}

	/*private void createNewEmployee() {

		Employee newHire = new Employee(fname, lname, employeeID);
	}*/

	private void changePassword(Employee e) { // Prompt employee to change password
		String newPassword; // upon login

	}

	private void viewPendingAccounts() {
		PendingAccounts.viewPendingCustomers();
	}

	private void approveNewCustomer(String username) {
		boolean customer = false;
		
		Iterator<AccountHolder> i = PendingAccounts.singleObj.pendingCustomers.iterator();
		while (i.hasNext()) {
			customer = AccountHolder.addNewAccountHolder(i.next(), username);
				if(customer == true)
					break;
		}
	}

	private void addCustomer(AccountHolder customer) {
		//customers.add(customer);
	}
	
	private void viewCustomers() {
		
		CustomerDao cdao = new CustomerDao();
		
		List<AccountHolder> customers = cdao.getCustomers();
		if(customers.isEmpty())
			System.out.println("No customers.");
		for (AccountHolder a : customers) {
			System.out.println(a.toString());
		}
		
	}
	
	private void viewAccounts() {
		
		AccountsDao adao = new AccountsDao();
		
		List<Accounts> accounts = adao.getAccounts();
		if(accounts.isEmpty())
			System.out.println("No customers.");
		for (Accounts a : accounts) {
			System.out.println(a.toString());
		}
		
	}
}
