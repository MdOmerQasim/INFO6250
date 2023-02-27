package neu.edu.controller.user;

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
 * Servlet implementation class UserViewAppointment
 */
@WebServlet("/userViewAppointment")
public class UserViewAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserViewAppointment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		
		ServletContext application = request.getServletContext();
		MongoClient mongoClient = (MongoClient) application.getAttribute("mongodbClient");
		AppointmentDAO dao = new AppointmentDAO(mongoClient);
		List<Appointment> appointments = dao.getListByUsername(userSession.getUsername());

        request.setAttribute("appointments", appointments);		
		request.getRequestDispatcher("/WEB-INF/user/viewAppointment.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
