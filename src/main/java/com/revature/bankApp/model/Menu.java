package com.revature.bankApp.model;

import java.util.List;
import java.util.Scanner;
import java.io.*;

import com.revature.bankApp.repository.*;

public class Menu {

	public static void main(String[] args) {
		
		//CustomerDao cdao = new CustomerDao();
		
		
		/*cdao.addCustomer(3, "Mckenna Rees", "rees", 9753);
		
		List<AccountHolder> customers = cdao.getCustomers();
		if(customers.isEmpty())
			System.out.println("No customers.");
		for (AccountHolder a : customers) {
			System.out.println(a.toString());
		}
		System.out.println("Finished");*/


		Accounts a;
		Scanner sc = new Scanner(System.in);

		//PendingAccounts.openPendingAccounts();
		//Accounts.openActiveAccounts();
		//PendingAccounts.initClass();
		//AccountHolder.init(); // Not keeping saved array list of active employees.
		//Employee systemAdmin = new Employee();

		System.out.println("\nWelcome to your BankApp!");
		System.out.println("Please select an input option below.\n");
		System.out.println("Login (1)\nApply for an account (2)\nEmployee Login (3)");
		System.out.println("\nExit App (0)");

		int option = sc.nextInt();

		if (option == 1)
			login(sc);
		else if (option == 2)
			apply(sc);
		else if (option == 3)
			employeeLogin(sc);
		else if (option == 0)
			System.out.println("Exiting...");
		else // Create loop to restart this menu.
			System.out.println("Please enter a valid option.");

		// System.out.println("Pending Customers:");
		// PendingAccounts.viewPendingCustomers();
		// System.out.println("Active Customers:");
		// AccountHolder.viewActiveCustomers();
		PendingAccounts.closePendingAccounts();
		AccountHolder.serialize();
		Accounts.closeAccountsList();
		sc.close();

	}

	static void login(Scanner sc) {
		CustomerDao cdao = new CustomerDao();
		System.out.println("User Login.");
		
		System.out.println("Please enter your username: ");
		String username = sc.next();
		System.out.println("Please enter your password: ");
		String password = sc.next();
		
		boolean isCustomer = cdao.verifyLogin(username, password);
		
		if (isCustomer == true) {
			System.out.println("Login Successful!");
			//AccountHolder c = new AccountHolder();
			AccountHolder.thingsToDo(username);
			
		}
		//AccountHolder.thingsToDo();
	}

	static void apply(Scanner sc) {

		System.out.println("Applying for account.");
		System.out.println("Please enter your first name: ");
		String fname = sc.next();
		System.out.println("Please enter your last name: ");
		String lname = sc.next();
		System.out.println("Please enter your preferred username: ");
		String userName = sc.next();

		new AccountHolder(fname, lname, userName);
		System.out.println("Thank you, you application is being processed.");

	}

	static void employeeLogin(Scanner sc) {
		
		EmployeeDao edao = new EmployeeDao();

		System.out.println("Employee Login.");
		System.out.println("Please enter your username: ");
		String username = sc.next();
		System.out.println("Please enter your password: ");
		String password = sc.next();

		//boolean isEmployee = Employee.verifyEmployeeCredentials(username, password);
		boolean isEmployee = edao.verifyLogin(username, password);
		
		if (isEmployee == true) {
			System.out.println("Login Successful!");
			Employee e = new Employee();
			e.thingsToDo(e);
			
		}
		

		 //Employee.viewEmployees(); 
		 //System.out.println(e.isAdmin());
		 

	}
}
