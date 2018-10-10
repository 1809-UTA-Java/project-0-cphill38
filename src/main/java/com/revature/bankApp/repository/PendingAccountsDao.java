package com.revature.bankApp.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.bankApp.model.AccountHolder;
import com.revature.bankApp.util.ConnectionUtil;

public class PendingAccountsDao {

	
	public void addPendingAccount(String username, String password, String name) {
		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO PEND_ACCOUNTS (username, password, name)"
					   + " VALUES ('" + username + "', '" + password + "', '" + name + "')";

			ps = conn.prepareStatement(sql);
			ps.executeQuery();

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
			String sql = "SELECT * FROM PEND_ACCOUNTS " + "WHERE username='" + username + "'";
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
	
	public List<AccountHolder> viewAccountApplications() {
		PreparedStatement ps = null;
		AccountHolder pc = null;
		List<AccountHolder> pendCustomers = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM PEND_ACCOUNTS";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String username = rs.getString("username");
				String password = rs.getString("password");

				pc = new AccountHolder(name, username, password);
				pendCustomers.add(pc);
			}

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}

		return pendCustomers;
	}
	
	public List<AccountHolder> getApplicant(String username) {
		PreparedStatement ps = null;
		AccountHolder c = null;
		List<AccountHolder> newCustomer = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM PEND_ACCOUNTS WHERE username='" + username +"'";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String uname = rs.getString("username");
				String password = rs.getString("password");

				c = new AccountHolder(name, uname, password);
				newCustomer.add(c);
			}

			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}

		return newCustomer;
	}
	
	public void removeApplicant(String username) {
		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM PEND_ACCOUNTS WHERE username='" + username +"'";
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
