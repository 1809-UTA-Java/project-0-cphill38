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
	// private boolean login = true;
	// private static ArrayList<Employee> employees = new ArrayList<>();

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

	/*
	 * public Employee() { // Testing right now for system admin. this.name =
	 * "System"; //this.lname = "Admin"; this.employeeID = 001; this.userName =
	 * "System"; this.password = "password"; this.isAdmin = true;
	 * employees.add(this); }
	 */

//	private Employee(String fname, String lname, int employeeID) {
//		this.name = fname;
//		// this.lname = lname;
//		this.employeeID = employeeID;
//		employeeBuilder(this);
//	}

//	private void employeeBuilder(Employee e) {
//		// this.userName = e.name + e.lname;
//		this.password = "password";
//		// this.login = false;
//
//	}

	public boolean isAdmin(Employee e) {
		if (e.isAdmin)
			System.out.println("User is admin!");
		else
			System.out.println("User is not a admin.");
		return e.isAdmin;
	}

//	public static void viewEmployees() {
//		Iterator<Employee> i = employees.iterator();
//		while (i.hasNext()) {
//			getEmployee(i.next());
//		}
//	}

	public static void getEmployee(Employee e) {
		System.out.println(e.name + " " + e.employeeID);
	}

	/*
	 * public static boolean verifyEmployeeCredentials(String userName, String
	 * password) {
	 * 
	 * boolean isEmployee = false; Iterator<Employee> i = employees.iterator();
	 * while (i.hasNext()) { isEmployee = getCreds(i.next(), userName, password); if
	 * (isEmployee == true) break; }
	 * 
	 * return isEmployee; }
	 * 
	 * private static boolean getCreds(Employee e, String userName, String password)
	 * { if (e.userName.equals(userName) && e.password.equals(password)) {
	 * e.thingsToDo(e); return true; } else return false; }
	 */

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
						+ "\nTransfer Funds (8)\nCancel Account (9)");
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
					e.approveApp(/* username */"");
					break;
				case 5:
					e.denyApp(/* username */"");
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

	/*
	 * private void createNewEmployee() {
	 * 
	 * Employee newHire = new Employee(fname, lname, employeeID); }
	 */

	private void changePassword(Employee e) { // Prompt employee to change password
		String newPassword; // upon login

	}

//	private void viewPendingAccounts() {
//		PendingAccounts.viewPendingCustomers();
//	}

//	private void approveNewCustomer(String username) {
//		boolean customer = false;
//
//		Iterator<AccountHolder> i = PendingAccounts.singleObj.pendingCustomers.iterator();
//		while (i.hasNext()) {
//			customer = AccountHolder.addNewAccountHolder(i.next(), username);
//			if (customer == true)
//				break;
//		}
//	}

	private void addCustomer(AccountHolder customer) {
		// customers.add(customer);
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

	private void createNewAccount() {
		CustomerDao cdao = new CustomerDao();
		AccountsDao adao = new AccountsDao();

		adao.createAccount(8884, 300.00);
		cdao.addCustomer(4, "John Doe", "doe", 8884);
	}

	private void cancelAccount(Employee e, Scanner sc) {
		AccountsDao adao = new AccountsDao();
		CustomerDao cdao = new CustomerDao();

		if (e.isAdmin) {
			System.out.println("\nWhich account do you want to cancel: ");
			int account = sc.nextInt();
			double balance = adao.getBalance(account);
			Accounts.withdraw(account, balance); // Withdraw remainder of funds in account.
			cdao.removeAccount(account); // Remove any foreign keys tied to account to cancel
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

	private void approveApp(String username) {
		PendingAccountsDao pdao = new PendingAccountsDao();
		CustomerDao cdao = new CustomerDao();

		List<AccountHolder> newCustomer = pdao.getApplicant(username);
		for (AccountHolder a : newCustomer) {
			cdao.addCustomer(5, a.getName(), a.getUsername(), a.getPassword());
		}
		pdao.removeApplicant(username);
	}

	private void denyApp(String username) {
		PendingAccountsDao pdao = new PendingAccountsDao();
		pdao.removeApplicant(username);
	}

}
