package com.revature.bankApp.repository;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.revature.bankApp.model.Employee;
import com.revature.bankApp.util.ConnectionUtil;

import java.math.*;

public class EmployeeDao implements Cloneable, Serializable {

	/**
	 * Persistent Instance variables. This data is directly mapped to the columns of
	 * database table.
	 */
	private int name;
	private String employeeID;
	private String userName;
	private String password;

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

	public EmployeeDao() {

	}

	public EmployeeDao(int nameIn) {

		this.name = nameIn;

	}

	/**
	 * Get- and Set-methods for persistent variables. The default behaviour does not
	 * make any checks against malformed data, so these might require some manual
	 * additions.
	 */

	public int getName() {
		return this.name;
	}

	public void setName(int nameIn) {
		this.name = nameIn;
	}

	public String getEmployeeID() {
		return this.employeeID;
	}

	public void setEmployeeID(String employeeIDIn) {
		this.employeeID = employeeIDIn;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userNameIn) {
		this.userName = userNameIn;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String passwordIn) {
		this.password = passwordIn;
	}

	public boolean verifyLogin(String username, String password) {
		
		boolean login = false;
		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM EMPLOYEES "
					   + "WHERE username='" + username + "'";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next())
				System.out.println("Username not found");
			else {
				String pw = rs.getString("password");
				if(pw.equals(password))
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
}
