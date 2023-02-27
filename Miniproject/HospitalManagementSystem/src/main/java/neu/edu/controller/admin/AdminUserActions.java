package neu.edu.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import neu.edu.dao.LoginDAO;
import neu.edu.dao.UserDAO;
import neu.edu.model.UserRegistration;

/**
 * Servlet implementation class AdminUserActions
 */
@WebServlet("/adminUserActions")
public class AdminUserActions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserActions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		if(action.equals("deleteUserData")) {
			String username = (String) request.getAttribute("username");
			UserDAO user = new UserDAO();
			user.deleteUser(username);
			
		} else if(action.equals("editUserData")) {
			String username = (String) request.getAttribute("username");
			UserDAO userDataFromDB = new UserDAO();
			UserRegistration userData = userDataFromDB.getUserDataByUsername(username);
			
			HttpSession session = request.getSession();
			session.setAttribute("userData", userData);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/editUser.jsp");
			rd.forward(request, response);
		} else if(action.equals("editUserDataPersist")) {
			HttpSession session = request.getSession();
			String oldUsername = ((UserRegistration)session.getAttribute("userData")).getUsername();
			String updatedUsername = (String) request.getParameter("username");
			String updatedEmail = (String) request.getParameter("email");
			String updatedFirstname = (String) request.getParameter("firstname");
			String updatedLastname = (String) request.getParameter("lastname");
			
			UserDAO user = new UserDAO();
			UserRegistration userData  = new UserRegistration();
			userData.setEmail(updatedEmail);
			userData.setFirstname(updatedFirstname);
			userData.setLastname(updatedLastname);
			userData.setUsername(updatedUsername);
			boolean flag = user.updateUserByAdmin(userData, oldUsername);
			request.setAttribute("updateFlag", "Successfully updated data!");
			RequestDispatcher rd = request.getRequestDispatcher("controller?action=viewUserEditStatus");
			rd.forward(request, response);
			
		} else if(action.equals("addUserDataPersist")) {
			String username = (String) request.getParameter("username");
			String email = (String) request.getParameter("email");
			String firstname = (String) request.getParameter("firstname");
			String lastname = (String) request.getParameter("lastname");
			String password = (String) request.getParameter("password");
			String role = (String) request.getParameter("role");
			
			LoginDAO data = new LoginDAO();
			
			UserRegistration userData  = new UserRegistration();
			userData.setEmail(email);
			userData.setFirstname(firstname);
			userData.setLastname(lastname);
			userData.setPassword(password);
			userData.setRole(role);
			userData.setUsername(username);
			boolean flag = data.insertUserData(userData);
			
			request.setAttribute("updateFlag", "Successfully added user!");
			RequestDispatcher rd = request.getRequestDispatcher("controller?action=viewUserAddStatus");
			rd.forward(request, response);
		}
			
	}

}
