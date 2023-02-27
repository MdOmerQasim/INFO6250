package neu.edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import neu.edu.model.UserRegistration;
import neu.edu.model.UserSession;
import neu.edu.database.DatabaseConnector;

public class LoginDAO {

	private Connection connection;

	public LoginDAO() {

	}

	public UserSession validateLogin(String username, String password) {

		PreparedStatement pst = null;
		UserSession userSession = null;

		try {
			connection = DatabaseConnector.getInstance().getConnection();
			pst = connection.prepareStatement("SELECT * FROM USER WHERE username=? and password=MD5(?)");
			pst.setString(1, username);
			pst.setString(2, password);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				String usernameFromDB = rs.getString("username");
				String emailFromDB = rs.getString("email");
				String roleFromDB = rs.getString("role");
				userSession = new UserSession(usernameFromDB, emailFromDB, roleFromDB);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userSession;

	}
	
	public boolean insertUserData(UserRegistration userRegistration) {
		System.out.println("Inside INSERT");
		PreparedStatement pst = null;
		boolean isValid = true;
		try {
			connection = DatabaseConnector.getInstance().getConnection();
			pst = connection.prepareStatement("INSERT INTO USER (username, password, email, role, first_name, last_name) VALUES (?, MD5(?), ?, ?, ?, ?)");
			pst.setString(1, userRegistration.getUsername());
			pst.setString(2, userRegistration.getPassword());
			pst.setString(3, userRegistration.getEmail());
			pst.setString(4, userRegistration.getRole().toString());
			pst.setString(5, userRegistration.getFirstname());
			pst.setString(6, userRegistration.getLastname());

			int rowsAffected = pst.executeUpdate();
			
		} catch (SQLException e) {
			isValid = false;
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isValid;
	}

}
