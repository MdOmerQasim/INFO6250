package neu.edu.email;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Servlet implementation class SendEmail
 */
@WebServlet("/sendEmail")
public class SendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendEmail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		String emailBody = (String) request.getAttribute("message");
		String emailSubject = (String) request.getAttribute("subject");
		String email = (String) request.getAttribute("email");

	    Properties properties = System.getProperties();
	    properties.setProperty("mail.smtp.host", "smtp-relay.sendinblue.com");
	    properties.setProperty("mail.smtp.port", "587");
	    properties.setProperty("mail.smtp.auth", "true");
	    //properties.setProperty("mail.smtp.starttls.enable", "true");

	    Session session = Session.getDefaultInstance(properties);

	    try {
	      MimeMessage mimeMessage = new MimeMessage(session);
	      mimeMessage.setFrom(new InternetAddress("shaik.moha@northeastern.edu"));
	      mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	      mimeMessage.setSubject(emailSubject);
	      mimeMessage.setText(emailBody);

	      Transport transport = session.getTransport("smtp");
	      transport.connect("smtp-relay.sendinblue.com", "omerqasim24@gmail.com", "DSa4E8XhQMJky6IB");
	      transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
	      transport.close();

//	      out.println("Email sent successfully!");
	    } catch (MessagingException me) {
//	      out.println("Error sending email: " + me.getMessage());
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
