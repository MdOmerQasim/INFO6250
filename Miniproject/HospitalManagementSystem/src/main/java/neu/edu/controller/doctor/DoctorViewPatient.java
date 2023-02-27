package neu.edu.controller.doctor;

import java.io.IOException;

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
import neu.edu.model.Appointment;

/**
 * Servlet implementation class DoctorViewPatient
 */
@WebServlet("/doctorViewPatient")
public class DoctorViewPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorViewPatient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = (String) request.getAttribute("id");
		System.out.println("ID - " + id);
		ServletContext application = request.getServletContext();
		MongoClient mongoClient = (MongoClient) application.getAttribute("mongodbClient");
		AppointmentDAO appt = new AppointmentDAO(mongoClient);
		Appointment appointment = (Appointment) appt.getAppointmentDataById(id);

		HttpSession session = request.getSession();
		session.setAttribute("appt", appointment);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/doctor/viewPatientData.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("action").equals("apptSubmit")) {
			String docNote = request.getParameter("notes");
			HttpSession session = request.getSession();
			Appointment appointment = (Appointment) session.getAttribute("appt");
			appointment.setDoctorNote(docNote);
			session.setAttribute("appt", appointment);
			
			//MONGO update
			ServletContext application = request.getServletContext();
			MongoClient mongoClient = (MongoClient) application.getAttribute("mongodbClient");
			AppointmentDAO dao = new AppointmentDAO(mongoClient);
			dao.update(appointment);
			
			request.setAttribute("successMsg", "Updated Successfully!");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/doctor/reviewStatus.jsp");
	        rd.forward(request, response);
			
			
			
		} else {
			doGet(request, response);
		}
		
	}

}
