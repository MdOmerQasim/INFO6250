package neu.edu.controller.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import neu.edu.dao.ProfileDAO;
import neu.edu.dao.UserDAO;
import neu.edu.model.UserRegistration;
import neu.edu.model.UserSession;
import neu.edu.model.UserRegistration.Role;

/**
 * Servlet implementation class UserProfile
 */
@WebServlet("/userProfile")
public class UserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String username = userSession.getUsername();
		String email = "";
		String telNum = "";
		String fullName = "";
		UserDAO user = new UserDAO();
		ArrayList<UserRegistration> userData = user.viewAllUsers("USER");
		for(UserRegistration obj: userData) {
			if(obj.getUsername().equals(username)) {
				email = obj.getEmail();
				telNum = obj.getLastname();
				fullName = obj.getFirstname();
			}
		}
		session.setAttribute("username", username);
		session.setAttribute("email", email);
		session.setAttribute("telNum", telNum);
		session.setAttribute("fullName", fullName);
		request.getRequestDispatcher("/WEB-INF/user/profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String username = userSession.getUsername();	
		String newPassword = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		
		if("editProfile".equals(request.getParameter("action"))){
			UserRegistration userData = new UserRegistration();
			userData.setUsername(username);
			userData.setEmail(request.getParameter("email"));
			userData.setFirstname(request.getParameter("firstname"));
			userData.setLastname(request.getParameter("lastname"));
			ProfileDAO profile = new ProfileDAO();
			profile.updateUserEmail(userData);
			
			String successMsg = "User profile updated!";
			request.setAttribute("successMsg",successMsg);
			
			session.setAttribute("username", username);
			session.setAttribute("email", userData.getEmail());
			session.setAttribute("telNum", userData.getLastname());
			session.setAttribute("fullName", userData.getFirstname());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/user/profile.jsp");
			rd.forward(request, response);
		} else if ("editProfilePassword".equals(request.getParameter("action"))) {
			if(!newPassword.equals(confirmPassword)) {
				String errorMsg = "password do not match!";
				request.setAttribute("errorMsg",errorMsg);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/user/profile.jsp");
				rd.forward(request, response);
			} else {
				//DB
				UserRegistration userData = new UserRegistration();
				userData.setUsername(username);
				userData.setPassword(confirmPassword);			
				ProfileDAO profile = new ProfileDAO();
				profile.updateUserPassword(userData);
				
				request.getSession().invalidate();
				request.getRequestDispatcher("/WEB-INF/user/passwordStatus.jsp").forward(request, response);
			}
		}
	}

}
