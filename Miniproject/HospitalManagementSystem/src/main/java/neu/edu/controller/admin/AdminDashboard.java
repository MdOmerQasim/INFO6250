package neu.edu.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.MongoClient;

import neu.edu.dao.AppointmentDAO;
import neu.edu.dao.UserDAO;
import neu.edu.model.Appointment;
import neu.edu.model.UserSession;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/adminDashboard")
public class AdminDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Show total appointment count
		ServletContext application = request.getServletContext();
		MongoClient mongoClient = (MongoClient) application.getAttribute("mongodbClient");
		AppointmentDAO appt = new AppointmentDAO(mongoClient);
		
		//User Data from MySQL
		UserDAO user = new UserDAO();
	
		request.setAttribute("patientCount", user.viewAllUsers("USER").size());
	    request.setAttribute("adminCount", user.viewAllUsers("ADMIN").size());
	    request.setAttribute("doctorCount", user.viewAllUsers("DOCTOR").size());
	    request.setAttribute("apptCount", appt.getList().size());
		
		request.getRequestDispatcher("/WEB-INF/admin/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
