package com.revature.bankApp.model;

import java.util.List;
import java.util.Scanner;

import com.revature.bankApp.repository.*;

public class Employee {

	private boolean isAdmin = false;
	private String name;
	private int employeeID;
	private String userName;
	private String password;

	public Employee() {
		super();
	}

	public Employee(int id, String name, String uname, String password, boolean admin) {
		super();
		this.employeeID = id;
		this.name = name;
		this.userName = uname;
		this.password = password;
		this.isAdmin = admin;
	}

	public boolean isAdmin(Employee e) {
		if (e.isAdmin)
			System.out.println("User is admin!");
		else
			System.out.println("User is not a admin.");
		return e.isAdmin;
	}

	public static void getEmployee(Employee e) {
		System.out.println(e.name + " " + e.employeeID);
	}

	public static void thingsToDo(String username) { // Menu for employees once they log-in.
		Scanner sc = new Scanner(System.in);
		EmployeeDao edao = new EmployeeDao();
		int option;
		boolean logout = false;

		System.out.println("\n-----Employee Menu-----");

		List<Employee> employee = edao.getEmployee(username);
		for (Employee e : employee) {
			while (!logout) {
				System.out.println("\nPlease select an input option below.\n");
				System.out.println("View Customers (1)\nView Accounts (2)\nView Customer Applications (3)"
						+ "\nApprove Application (4)\nDeny Application (5)\n"
						+ "\n---Admin Options---\nWithdraw Funds (6)\nDeposit Funds (7)"
						+ "\nTransfer Funds (8)\nCancel Account (9)\nCreate Account (10)");
				System.out.println("\nLogout (0)");

				option = sc.nextInt();
				switch (option) {
				case 1:
					e.viewCustomers();
					break;
				case 2:
					e.viewAccounts();
					break;
				case 3:
					e.viewApps();
					break;
				case 4:
					e.approveApp(e, sc);
					break;
				case 5:
					e.denyApp(e, sc);
					break;
				case 6:
					e.withdrawFunds(e, sc);
					break;
				case 7:
					e.depositFunds(e, sc);
					break;
				case 8:
					e.transferFunds(e, sc);
					break;
				case 9:
					e.cancelAccount(e, sc);
					break;
				case 10:
					e.createNewAccount(e, sc);
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
		return "Employee [isAdmin=" + isAdmin + ", name=" + name + ", employeeID=" + employeeID + ", userName="
				+ userName + "]";
	}

	private void viewCustomers() {

		CustomerDao cdao = new CustomerDao();

		List<AccountHolder> customers = cdao.getCustomers();
		if (customers.isEmpty())
			System.out.println("No customers.");
		for (AccountHolder a : customers) {
			System.out.println(a.toString());
		}

	}

	private void viewAccounts() {

		AccountsDao adao = new AccountsDao();

		List<Accounts> accounts = adao.getAccounts();
		if (accounts.isEmpty())
			System.out.println("No customers.");
		for (Accounts a : accounts) {
			System.out.println(a.toString());
		}

	}

	private void withdrawFunds(Employee e, Scanner sc) { // Include checking to verify account exist.
		if (e.isAdmin) {
			System.out.println("\nwithdraw Menu\n");
			System.out.print("Which account are you withdrawing from: ");
			int accountNum = sc.nextInt();
			System.out.print("How much would you like to withdraw: ");
			double amount = sc.nextDouble();
			Accounts.withdraw(accountNum, amount);
		} else
			System.out.println("Only admins have access to this feature");

	}

	private void depositFunds(Employee e, Scanner sc) { // Include checking to verify account exist.

		if (e.isAdmin) {
			System.out.println("\nDeposit Menu\n");
			System.out.print("Which account are you depositing to: ");
			int accountNum = sc.nextInt();
			System.out.print("How much would you like to deposit: ");
			double amount = sc.nextDouble();
			Accounts.deposit(accountNum, amount);
		} else
			System.out.println("Only admins have access to this feature");

	}

	private void transferFunds(Employee e, Scanner sc) { // Include checking to verify account exist.

		if (e.isAdmin) {
			System.out.println("\nTransfer Menu\n");
			System.out.print("Which account would you like to transfer funds from: ");
			int accountOut = sc.nextInt();
			System.out.print("Which account would you like to transfer funds to: ");
			int accountIn = sc.nextInt();
			System.out.print("How much would you like to transfer: ");
			double amount = sc.nextDouble();
			Accounts.transferFunds(accountOut, accountIn, amount);
		} else
			System.out.println("Only admins have access to this feature");

	}

	private void createNewAccount(Employee e, Scanner sc) {
		AccountsDao adao = new AccountsDao();

		if (e.isAdmin) {
			System.out.print("Enter new account number:");
			int num = sc.nextInt();
			adao.createAccount(num, 0.00);
			e.linkAccount(sc, num);

		} else
			System.out.println("Only admins have access to this feature");
	}

	private void linkAccount(Scanner sc, int num) {
		CustomerDao cdao = new CustomerDao();
		AccountsDao adao = new AccountsDao();
		
		System.out.print("Enter customer ID to link:");
		int id = sc.nextInt();
		cdao.linkAccount(num, id);
		adao.linkAccounts(num, id);
			
	}
	
	//private void linkJointAccounts();

	private void cancelAccount(Employee e, Scanner sc) {
		AccountsDao adao = new AccountsDao();
		CustomerDao cdao = new CustomerDao();

		if (e.isAdmin) {
			System.out.println("\nWhich account do you want to cancel: ");
			int account = sc.nextInt();
			double balance = adao.getBalance(account);
			Accounts.withdraw(account, balance); // Withdraw remainder of funds in account.
			cdao.removeAccount(account); // Remove any foreign keys tied to account to cancel
			adao.removeLinks(account); // Remove any foreign keys from ACCOUNT_CUSTOMER_LIST
			adao.deleteAccount(account);// Remove account

		} else
			System.out.println("Only admins have access to this feature");

	}

	private void viewApps() {
		PendingAccountsDao pdao = new PendingAccountsDao();

		List<AccountHolder> applicants = pdao.viewAccountApplications();
		for (AccountHolder a : applicants) {
			System.out.println(a.toString());
		}
	}

	private void approveApp(Employee e, Scanner sc) {
		PendingAccountsDao pdao = new PendingAccountsDao();
		CustomerDao cdao = new CustomerDao();

		e.viewApps();
		System.out.print("Type the username of the customer you\n" + "want to approve:");
		String username = sc.next();
		System.out.print("Enter new customers ID:");
		int id = sc.nextInt();

		List<AccountHolder> newCustomer = pdao.getApplicant(username);
		for (AccountHolder a : newCustomer) {
			cdao.addCustomer(id, a.getName(), a.getUsername(), a.getPassword());
		}
		pdao.removeApplicant(username);

		System.out.println("Customer Approved Successfully!");
	}

	private void denyApp(Employee e, Scanner sc) {
		PendingAccountsDao pdao = new PendingAccountsDao();

		e.viewApps();
		System.out.print("Type the username of the customer to deny:");
		String username = sc.next();
		pdao.removeApplicant(username);
		System.out.println("Application removed.");
	}

}
