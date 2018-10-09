package com.revature.bankApp.repository;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.revature.bankApp.model.AccountHolder;
import com.revature.bankApp.model.Accounts;
import com.revature.bankApp.util.ConnectionUtil;

import java.math.*;

public class AccountsDao implements Cloneable, Serializable {

	/**
	 * Persistent Instance variables. This data is directly mapped to the columns of
	 * database table.
	 */
	private int accountID;
	private double balance;

	/**
	 * Non-persistent Instance variables. These are not mapped to database table,
	 * and their values are can only exists in runtime memory.
	 */

	/*
	 * private String ; private String ; private String ; private String ;
	 */

	/**
	 * Constructors. DaoGen generates two constructors by default. The first one
	 * takes no arguments and provides the most simple way to create object
	 * instance. The another one takes one argument, which is the primary key of the
	 * corresponding table.
	 */

	public AccountsDao() {

	}

	/*
	 * public AccountsDao(int nameIn) {
	 * 
	 * this.name = nameIn;
	 * 
	 * }
	 */

	/**
	 * Get- and Set-methods for persistent variables. The default behaviour does not
	 * make any checks against malformed data, so these might require some manual
	 * additions.
	 */

	public int getAccountID() {
		return this.accountID;
	}

	public void setAccountID(int accountIDIn) {
		this.accountID = accountIDIn;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balanceIn) {
		this.balance = balanceIn;
	}

	public List<Accounts> getAccounts() {
		PreparedStatement ps = null;
		Accounts a = null;
		List<Accounts> accounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Accounts";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("a_id");
				double balance = rs.getDouble("balance");

				a = new Accounts(id, balance);
				accounts.add(a);
			}

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}

		return accounts;
	}

	public void createAccount(int accountID, double balance) {
		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO ACCOUNTS (a_id, balance)" + " VALUES (" + accountID + ", '" + balance + "')";
			ps = conn.prepareStatement(sql);
			ps.executeQuery();

			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
	}

	public void updateBalance(int accountID, double newBalance) {
		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ACCOUNTS " + "SET balance = " + newBalance + " WHERE a_id = " + accountID;
			ps = conn.prepareStatement(sql);
			ps.executeQuery();

			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
	}

	public double getBalance(int accountID) {
		PreparedStatement ps = null;
		double balance = 0;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT BALANCE FROM ACCOUNTS " + "WHERE a_id=" + accountID;
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				balance = rs.getDouble("balance");
			}

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}

		return balance;
	}
	
	public void deleteAccount(int accountNum) {

		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM ACCOUNTS WHERE a_id=" + accountNum;
			
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
	}
	
}
