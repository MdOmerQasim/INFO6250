package neu.edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import neu.edu.model.UserRegistration;
import neu.edu.database.DatabaseConnector;

public class ProfileDAO {

	private Connection connection;

	public ProfileDAO() {

	}
	
	public boolean updateUserPassword(UserRegistration userRegistration) {
		
		PreparedStatement pst = null;
		boolean isValid = true;
		try {
			connection = DatabaseConnector.getInstance().getConnection();
			pst = connection.prepareStatement("UPDATE USER SET PASSWORD = MD5(?) WHERE USERNAME = ?");
			pst.setString(1, userRegistration.getPassword());
			pst.setString(2, userRegistration.getUsername());
			
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
	
	public boolean updateUserEmail(UserRegistration userRegistration) {
			
			PreparedStatement pst = null;
			boolean isValid = true;
			try {
				connection = DatabaseConnector.getInstance().getConnection();
				pst = connection.prepareStatement("UPDATE USER SET EMAIL = ?, FIRST_NAME = ?, LAST_NAME = ? WHERE USERNAME = ?");
				pst.setString(1, userRegistration.getEmail());
				pst.setString(2, userRegistration.getFirstname());
				pst.setString(3, userRegistration.getLastname());
				pst.setString(4, userRegistration.getUsername());
				
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
