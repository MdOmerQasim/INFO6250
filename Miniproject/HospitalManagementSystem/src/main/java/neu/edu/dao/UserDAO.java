package neu.edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import neu.edu.model.UserRegistration;
import neu.edu.model.UserSession;
import neu.edu.database.DatabaseConnector;

public class UserDAO {
	
	private Connection connection;

	public UserDAO() {

	}

	public ArrayList<UserRegistration> viewAllUsers(String role) {

		PreparedStatement pst = null;
		ArrayList<UserRegistration> userRegistrations = new ArrayList();

		try {
			connection = DatabaseConnector.getInstance().getConnection();
			if(role != null) {
				pst = connection.prepareStatement("SELECT * FROM USER WHERE ROLE=(?)");
				pst.setString(1, role);
			}else {
				pst = connection.prepareStatement("SELECT * FROM USER");

			}

			ResultSet rs = pst.executeQuery();
		

			while (rs.next()) {


				String usernameFromDB = rs.getString("username");
				String emailFromDB = rs.getString("email");
				String roleFromDB = rs.getString("role");
				String firstNameFromDB = rs.getString("first_name");
				String lastNameFromDB = rs.getString("last_Name");
				
//				System.out.println(usernameFromDB + " " +emailFromDB + " " + roleFromDB);

				UserRegistration userRegistration = new UserRegistration(usernameFromDB, emailFromDB, roleFromDB, firstNameFromDB, lastNameFromDB);
				userRegistrations.add(userRegistration);
				
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
		return userRegistrations;

	}
	
	public UserRegistration getUserDataByUsername(String username) {

		PreparedStatement pst = null;
		UserRegistration userRegistration  = new UserRegistration();
		try {
			connection = DatabaseConnector.getInstance().getConnection();
			pst = connection.prepareStatement("SELECT * FROM USER WHERE USERNAME=(?)");
			pst.setString(1, username);
			
			ResultSet rs = pst.executeQuery();
		

			while (rs.next()) {


				String usernameFromDB = rs.getString("username");
				String emailFromDB = rs.getString("email");
				String roleFromDB = rs.getString("role");
				String firstNameFromDB = rs.getString("first_name");
				String lastNameFromDB = rs.getString("last_Name");
				
				userRegistration = new UserRegistration(usernameFromDB, emailFromDB, roleFromDB, firstNameFromDB, lastNameFromDB);
				
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
		return userRegistration;

	}
	
	public boolean deleteUser(String username) {
		
		PreparedStatement pst = null;
		boolean deleted = true;
		try {
			connection = DatabaseConnector.getInstance().getConnection();
			pst = connection.prepareStatement("DELETE FROM USER WHERE username=?");
			pst.setString(1, username);

			int rowsAffected = pst.executeUpdate();
			
		} catch (SQLException e) {
			deleted = false;
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return deleted;
		
	}
	
	public boolean updateUser(UserRegistration userRegistration) {
		PreparedStatement pst = null;
		boolean isValid = true;
		try {
			connection = DatabaseConnector.getInstance().getConnection();
				pst = connection.prepareStatement("UPDATE USER SET FIRST_NAME = ?, LAST_NAME = ?, PASSWORD = MD5(?), EMAIL = ? WHERE USERNAME = ?");
				pst.setString(1, userRegistration.getFirstname());
				pst.setString(2, userRegistration.getLastname());
				pst.setString(3, userRegistration.getPassword());
				pst.setString(4, userRegistration.getEmail());
				pst.setString(5, userRegistration.getUsername());
			
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
	
	public boolean updateUserByAdmin(UserRegistration userRegistration, String oldUserName) {
		System.out.println("Inside updateUser" + userRegistration.getUsername());
		PreparedStatement pst = null;
		boolean isValid = true;
		try {
			connection = DatabaseConnector.getInstance().getConnection();
			System.out.println("updateUser" + userRegistration.getPassword());
				System.out.println("updateUser2");
				pst = connection.prepareStatement("UPDATE USER SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, USERNAME = ? WHERE USERNAME = ?");
				pst.setString(1, userRegistration.getFirstname());
				pst.setString(2, userRegistration.getLastname());
				pst.setString(3, userRegistration.getEmail());
				pst.setString(4, userRegistration.getUsername());
				pst.setString(5, oldUserName); 
				System.out.println("updateUser3");
			
			
			
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
