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

public class PendingAccounts implements Serializable {

	static PendingAccounts singleObj;
	private static final long serialVersionUID = 1L;
	private static String fileName = "pendingAccounts";
	ArrayList<AccountHolder> pendingCustomers = new ArrayList<>();

	private PendingAccounts() {
		System.out.println("New PendingAccounts object created");
	}

	public static void initClass() {
		if (singleObj == null)
			singleObj = new PendingAccounts();
		else
			System.out.println("Instance Already Created.");
	}

	public static boolean instanceExist() {
		if (singleObj == null)
			return false;
		else
			return true;
	}

	/*
	 * public static PendingAccounts getSingleObj() { return singleObj; }
	 */

	static void addPendingCustomer(AccountHolder pendingCustomer) {
		singleObj.pendingCustomers.add(pendingCustomer);
	}

	/*
	 * public static boolean getPendingCustomers() { 
	 * 	if (singleObj.pendingCustomers.isEmpty()) 
	 * 		return false; 
	 * 	else 
	 * 		return true;
	 * 
	 * }
	 */

//	static void viewPendingCustomers() {
//		Iterator<AccountHolder> iterator = singleObj.pendingCustomers.iterator();
//		while (iterator.hasNext()) {
//			AccountHolder.viewPendingCustomers(iterator.next());
//		}
//	}
	
	/*static AccountHolder getPendingCustomers(String username) {
		Object obj = null;
		
		Iterator<AccountHolder> iterator = singleObj.pendingCustomers.iterator();
		while (iterator.hasNext()) {
			obj = AccountHolder.getNewAccountHolder(iterator.next(), username);
		}
		
		return (AccountHolder)obj;
	}*/

	// Serialize the object before exiting.
	public static void closePendingAccounts() {

		if (instanceExist() == true) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
				oos.writeObject(singleObj);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else
			System.out.println("No Object to Serialize");
	}

	// Opens the serialized object when application is ran.
	public static void openPendingAccounts() {

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
			Object obj = ois.readObject();
			singleObj = (PendingAccounts) obj;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

}
