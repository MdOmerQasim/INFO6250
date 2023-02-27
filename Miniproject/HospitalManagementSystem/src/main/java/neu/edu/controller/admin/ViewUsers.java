package neu.edu.controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import neu.edu.dao.UserDAO;
import neu.edu.model.UserRegistration;

/**
 * Servlet implementation class ViewUsers
 */
@WebServlet("/viewUsers")
public class ViewUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		UserDAO userDAO = new UserDAO();
		ArrayList<UserRegistration> userRegistrations = userDAO.viewAllUsers(null);
		HttpSession session = request.getSession(); 
		session.setAttribute("userRegistrations", userRegistrations);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/viewUsers.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
