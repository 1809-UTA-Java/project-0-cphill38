package com.revature.bankApp.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.bankApp.util.ConnectionUtil;

public class PendingAccountsDao {

	
	public void addPendingAccount(String username, String password) {
		PreparedStatement ps = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO PEND_ACCOUNTS (username, password)" + " VALUES ('" + username + "', '" + password + "')";

			ps = conn.prepareStatement(sql);
			ps.executeQuery();

			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
	}
	
}
