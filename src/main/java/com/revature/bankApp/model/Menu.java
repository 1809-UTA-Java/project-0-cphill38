package com.revature.bankApp.model;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.bankApp.repository.CustomerDao;
import com.revature.bankApp.repository.EmployeeDao;
import com.revature.bankApp.repository.PendingAccountsDao;

public class Menu {
	
	final static Logger logger = Logger.getLogger(Menu.class);

	public static void main(String[] args) {
		boolean exit = false;
		int option;
		Scanner scOption = new Scanner(System.in);
		Scanner scInput = new Scanner(System.in);

		System.out.println("\nWelcome to your BankApp!");

		while (!exit) {
			System.out.println("Please select an input option below.\n");
			System.out.println("Login (1)\nApply for an account (2)\nEmployee Login (3)");
			System.out.println("\nExit App (0)");
			
			
			option = scOption.nextInt();
			
			switch (option) {
			case 1:
				login(scInput);
				break;
			case 2:
				apply(scInput);
				break;
			case 3:
				employeeLogin(scInput);
				break;
			case 0:
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid Option.");
			}
			exit = true;
		}

		scOption.close();
		scInput.close();
	}

	static void login(Scanner sc) {
		logger.info("Entering customer login method");
		CustomerDao cdao = new CustomerDao();
		System.out.println("User Login.");

		System.out.println("Please enter your username: ");
		String username = sc.next();
		System.out.println("Please enter your password: ");
		String password = sc.next();

		boolean isCustomer = cdao.verifyLogin(username, password);

		if (isCustomer == true) {
			System.out.println("Login Successful!");
			AccountHolder.thingsToDo(username);
		}
	}

	static void apply(Scanner sc) {
		logger.info("Entering new application method.");
		boolean usernameExists = true;
		String username;
		String password;
		String name;
		CustomerDao cdao = new CustomerDao();
		PendingAccountsDao pdao = new PendingAccountsDao();

		System.out.println("Applying for account.");

		while (usernameExists) {
			System.out.print("Please enter your preferred username: ");
			username = sc.next();
			usernameExists = cdao.checkUsernames(username);
			if (!usernameExists) {
				usernameExists = pdao.checkUsernames(username);
				if (!usernameExists) {
					System.out.print("\nPlease choose a password: ");
					password = sc.next();
					System.out.print("\nPlease enter your first name: ");
					String fname = sc.next();
					System.out.print("\nPlease enter your last name: ");
					String lname = sc.next();
					name = fname + " " + lname;
					pdao.addPendingAccount(username, password, name);
					System.out.println("Thank you, your application is being processed.");
				} else
					System.out.println("Username taken, please try again.\n");
			} else
				System.out.println("Username taken, please try again.\n");
		}
	}

	static void employeeLogin(Scanner sc) {
		logger.info("Entering Employee Login Method.");
		EmployeeDao edao = new EmployeeDao();

		System.out.println("Employee Login.");
		System.out.println("Please enter your username: ");
		String username = sc.next();
		System.out.println("Please enter your password: ");
		String password = sc.next();

		boolean isEmployee = edao.verifyLogin(username, password);

		if (isEmployee == true) {
			System.out.println("Login Successful!");
			Employee.thingsToDo(username);
		}
	}
}
