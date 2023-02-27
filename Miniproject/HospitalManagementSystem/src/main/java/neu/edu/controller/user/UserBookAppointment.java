package neu.edu.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import neu.edu.model.UserRegistration;
import neu.edu.model.UserSession;

/**
 * Servlet implementation class UserBookAppointment
 */
@WebServlet("/userBookAppointment")
public class UserBookAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserBookAppointment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		UserDAO user = new UserDAO();
		ArrayList<UserRegistration> docData = user.viewAllUsers("DOCTOR");
		List<String> doctorNames = new ArrayList<>();
		
		for (UserRegistration doctorName : docData) {
			doctorNames.add(doctorName.getFirstname());
		}
		
		request.setAttribute("doctorNames", doctorNames);
		request.getRequestDispatcher("/WEB-INF/user/bookAppointment.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		
		UserDAO user = new UserDAO();
		ArrayList<UserRegistration> docData = user.viewAllUsers("DOCTOR");
		List<String> doctorNames = new ArrayList<>();
		String doc_username = "";
		for (UserRegistration doctorName : docData) {
			if(doctorName.getFirstname().equals(request.getParameter("doctor"))) {
				doc_username = doctorName.getUsername();
			}
		}
		
		UserRegistration userData = user.getUserDataByUsername(userSession.getUsername());
		
		
		Appointment appt = new Appointment();
		appt.setUsername(userSession.getUsername()); 
		appt.setPatientName(userData.getFirstname());
		appt.setEmail(userSession.getEmail());
		appt.setPhoneNum(userData.getLastname());
		appt.setDoctorName(request.getParameter("doctor"));
		appt.setDoctorUserName(doc_username);
		appt.setApptDate(request.getParameter("date"));
		appt.setMessage(request.getParameter("message"));
		appt.setDoctorNote("Awaiting Doctor review");
		
		ServletContext application = request.getServletContext();
		MongoClient mongoClient = (MongoClient) application.getAttribute("mongodbClient");
		AppointmentDAO dao = new AppointmentDAO(mongoClient);
		dao.create(appt);
		
		//Send email
		String emailMessage = "Appointment scheduled with " + appt.getDoctorName() + " on " + appt.getApptDate();
		String subject = "APPOINTMENT BOOKED | WECARE";
		
		request.setAttribute("message", emailMessage);
		request.setAttribute("subject", subject);
		request.setAttribute("email", appt.getEmail());
		RequestDispatcher emailDispatcher = request.getRequestDispatcher("sendEmail");		
		response.setBufferSize(10 * 1024);
		response.resetBuffer();
		emailDispatcher.include(request, response);

		request.getRequestDispatcher("/WEB-INF/user/appointmentStatus.jsp").forward(request, response);

	}

}
