package neu.edu.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neu.edu.dao.LoginDAO;
import neu.edu.model.UserRegistration;

/**
 * Servlet implementation class Register
 */
@WebServlet(urlPatterns = {"/Registration","/registration"})
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/registration.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		
		UserRegistration userRegistration = new UserRegistration(username, password, email,UserRegistration.Role.USER, firstname, lastname);
		
		ServletContext application = request.getServletContext();
		
		ArrayList<UserRegistration> userRegistrations = (ArrayList<UserRegistration>) application.getAttribute("userRegistrations");
		
		if(userRegistrations == null) {
			userRegistrations = new ArrayList<UserRegistration>();
		}
		
		userRegistrations.add(userRegistration);
		application.setAttribute("userRegistrations", userRegistrations);
		
		
		// INSERT into MySQL
		LoginDAO loginDAO = new LoginDAO();
		if(!loginDAO.insertUserData(userRegistration)) {
			String errorMsg = "username already taken!";
			request.setAttribute("errorMsg",errorMsg);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/registration.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/registrationStatus.jsp");
			rd.forward(request, response);
		}

		
	}

}
