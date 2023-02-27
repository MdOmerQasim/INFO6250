package neu.edu.database;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import javax.servlet.ServletContext;
/**
 * Servlet implementation class TestMongoDb
 */
@WebServlet("/TestMongoDb")
public class TestMongoDb extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestMongoDb() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String name = "";
        ServletContext application = request.getServletContext();
        MongoClient mongoClient = (MongoClient) application.getAttribute("mongodbClient");
        MongoCollection<Document> mongoCollectionUsers = mongoClient.getDatabase("hospitalManagementSystem").getCollection("users");
//        MongoCursor<Document>  cursor = mongoCollectionUsers.find().iterator();
        FindIterable<Document> cursor2 = mongoCollectionUsers.find();
            try {           	
            	for (Document doc : cursor2) {
            	    String patientName = doc.getString("patient_name");
            	    String doctorName = doc.getString("doctor_name");

            	    System.out.println("Patient name: " + patientName);
            	    System.out.println("Doctor name: " + doctorName);
            	}
            	
            } finally {
//                cursor.close();
            }
            response.getWriter().append(name).append(request.getContextPath());
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}