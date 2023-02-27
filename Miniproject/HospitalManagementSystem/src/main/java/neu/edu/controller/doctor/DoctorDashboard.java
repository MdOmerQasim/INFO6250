package neu.edu.controller.doctor;

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
import neu.edu.model.Appointment;
import neu.edu.model.UserSession;

/**
 * Servlet implementation class DoctorDashboard
 */
@WebServlet("/doctorDashboard")
public class DoctorDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorDashboard() {
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
		
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute("userSession");
		List<Appointment> app = appt.getListByDoctorName(userSession.getUsername());

		int reviewedCount = 0;
		int apptCount = 0;
		
		for(Appointment ap: app) {
			if(ap.getDoctorNote().equals("Awaiting Doctor review")) {
				apptCount+=1;
			} else {
				reviewedCount+=1;
			}
		}
		
		
		
		
		
		request.setAttribute("apptCount", apptCount);
		request.setAttribute("reviewedCount", reviewedCount);
		request.getRequestDispatcher("/WEB-INF/doctor/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
