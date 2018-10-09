package com.revature.bankApp.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.bankApp.util.ConnectionUtil;
import com.revature.bankApp.model.AccountHolder;

public class CustomerDao {

	public List<AccountHolder> getCustomers() {
		PreparedStatement ps = null;
		AccountHolder c = null;
		List<AccountHolder> customers = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM CUSTOMERS";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("c_id");
				String name = rs.getString("name");
				String username = rs.getString("username");
				int accountID = rs.getInt("account");
				String password = rs.getString("password");

				c = new AccountHolder(id, name, username, accountID, password);
				customers.add(c);
			}

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}

		return customers;
	}

	public List<AccountHolder> getCustomer(String username) {
		PreparedStatement ps = null;
		AccountHolder c = null;
		List<AccountHolder> customer = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM CUSTOMERS " + "WHERE username='" + username + "'";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("c_id");
				String name = rs.getString("name");
				String uname = rs.getString("username");
				int accountID = rs.getInt("account");
				String password = rs.getString("password");

				c = new AccountHolder(id, name, uname, accountID, password);
				customer.add(c);
			}

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}

		return customer;
	}

	public void addCustomer(int custID, String name, String username, int account) {
		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO Customers (c_id, name, username, account)" + " VALUES (" + custID + ", '" + name
					+ "', '" + username + "', " + account + ")";
			ps = conn.prepareStatement(sql);
			ps.executeQuery();

			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
	}
	
	public void addCustomer(int custID, String name, String username, String password) {
		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO Customers (c_id, name, username, password)" + " VALUES (" + custID + ", '" + name
					+ "', '" + username + "', '" + password + "')";
			ps = conn.prepareStatement(sql);
			ps.executeQuery();

			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
	}

	public boolean verifyLogin(String username, String password) {

		boolean login = false;
		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM CUSTOMERS " + "WHERE username='" + username + "'";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				System.out.println("Username not found");
			else {
				String pw = rs.getString("password");
				if (pw.equals(password))
					login = true;
				else
					System.out.println("username/password combination incorrect.");
			}

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}

		return login;
	}
	
	public void removeAccount(int accountNum) {

		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE CUSTOMERS SET account=NULL WHERE account=" + accountNum;
			
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
	
	public boolean checkUsernames(String username) {
		PreparedStatement ps = null;
		boolean duplicate = true;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM CUSTOMERS " + "WHERE username='" + username + "'";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if(rs.next()) 
				duplicate = true;
			else
				duplicate = false;
			
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}

		return duplicate;
	}

}
