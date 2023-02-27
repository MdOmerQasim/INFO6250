package neu.edu.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import neu.edu.model.UserRegistration;
import neu.edu.model.UserSession;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/controller")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
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

		String action = request.getParameter("action");
		RequestDispatcher rd = null;

		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		UserRegistration.Role role = userSession.getRole();

		String currentPage = userSession.getCurrentPage();
		String newAction = null;
		String dispatcher = "/login";
		
		switch (role) {
		case ADMIN:
			if ("adminDashboard".equals(currentPage)) {
				
				if (action == null) {
					dispatcher = "adminDashboard";
				} else if ("viewUsers".equals(action)) {
					dispatcher = "viewUsers";
				} else if ("viewDoctors".equals(action)) {
					dispatcher = "viewDoctors";
				} else if ("deleteUserData".equals(action)) {
					String username = request.getParameter("username");
					request.setAttribute("username", username);
					dispatcher = "adminUserActions?action=deleteUserData";
				} else if ("deleteUser".equals(action)) {
					request.setAttribute("deleteFlag", "Deleted Successfully!");
					dispatcher = "/WEB-INF/admin/deleteUserStatus.jsp";
				} else if ("editUserData".equals(action)) {
					String username = request.getParameter("username");
					request.setAttribute("username", username);
					dispatcher = "adminUserActions?action=editUserData";
				} else if ("editUser".equals(action)) {
					dispatcher = "/WEB-INF/admin/editUser.jsp";
				} else if ("addUserData".equals(action)) {
					dispatcher = "/WEB-INF/admin/addUser.jsp";
				} else if ("addUser".equals(action)) {
					dispatcher = "/WEB-INF/admin/addUser.jsp";
				} else if ("viewUserAddStatus".equals(action)) {
					dispatcher = "/WEB-INF/admin/addUserStatus.jsp";
				} else if ("viewUserEditStatus".equals(action)) {
					dispatcher = "/WEB-INF/admin/editUserStatus.jsp";
				} else if ("viewUserDeleteStatus".equals(action)) {
					dispatcher = "/WEB-INF/admin/deleteUserStatus.jsp";
				} else if ("profile".equals(action)) {
					dispatcher = "adminProfile";
				} 

			}

			rd = request.getRequestDispatcher(dispatcher);
			rd.forward(request, response);

			break;

		case DOCTOR:
			if ("doctorDashboard".equals(currentPage)) {

				if (action == null) {
					dispatcher = "doctorDashboard";
				} else if ("profile".equals(action)) {
					dispatcher = "doctorProfile";
				} else if ("viewAppointment".equals(action)) {
					dispatcher = "doctorViewAppointment";
				} else if ("viewPatient".equals(action)) {
					dispatcher = "/WEB-INF/doctor/viewPatientData.jsp";
				} else if ("viewPatientData".equals(action)) {
					String id = request.getParameter("id");
					request.setAttribute("id", id);
					dispatcher = "doctorViewPatient";
				} 

			}

			rd = request.getRequestDispatcher(dispatcher);
			rd.forward(request, response);

			break;

		case USER:

			if ("userDashboard".equals(currentPage)) {

				if (action == null) {
					dispatcher = "userDashboard";

				} else if ("profile".equals(action)) {
					userSession.setCurrentPage(currentPage);
					dispatcher = "userProfile";
				} else if ("bookAppointment".equals(action)) {
					dispatcher = "userBookAppointment";
				} else if ("viewAppointment".equals(action)) {
					dispatcher = "userViewAppointment";
				}

			} else if ("userProfile".equals(currentPage)) {

				if (action == null) {
					userSession.setCurrentPage("userDashboard");
				} else if ("profile".equals(action) || "editProfile".equals(action)
						|| "editProfilePassword".equals(action)) {
					dispatcher = "userProfile";
				} else if ("bookAppointment".equals(action)) {
					dispatcher = "userBookAppointment";
				} else if ("viewAppointment".equals(action)) {
					dispatcher = "userViewAppointment";
				}
			} 

			rd = request.getRequestDispatcher(dispatcher);
			rd.forward(request, response);
			break;

		default:
			session.invalidate();
			System.out.println("loggingoutbro");
//			response.sendRedirect("index.html");
			rd = request.getRequestDispatcher("/WEB-INF/logoutStatus.jsp");
			rd.forward(request, response);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
