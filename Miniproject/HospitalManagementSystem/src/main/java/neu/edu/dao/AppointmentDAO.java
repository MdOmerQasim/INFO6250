package neu.edu.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import neu.edu.model.Appointment;
import neu.edu.convertor.AppointmentConvertor;

public class AppointmentDAO {

	private MongoCollection<Document> mongoCollectionUsers;

	public AppointmentDAO(MongoClient mongo) {
		this.mongoCollectionUsers = mongo.getDatabase("hospitalManagementSystem").getCollection("appointments");
	}
	
	public Appointment create(Appointment appt) {
        Document apptDoc = AppointmentConvertor.toDocument(appt);
        this.mongoCollectionUsers.insertOne(apptDoc);
        ObjectId id = (ObjectId) apptDoc.get("_id");
        appt.setId(id.toString());
        return appt;
    }
 
    public void update(Appointment appt) {
        this.mongoCollectionUsers.updateOne(Filters.eq("_id", new ObjectId(appt.getId())), new Document("$set", AppointmentConvertor.toDocument(appt)));
    }
 
    public void delete(String id) {
        this.mongoCollectionUsers.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    
    public Appointment getAppointmentDataById(String id) {
        ObjectId objectId = null;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            // Handle the case where the string is not a valid ObjectID
            return null;
        }
        Bson filter = Filters.eq("_id", objectId);
        Document doc = this.mongoCollectionUsers.find(filter).first();
        if (doc == null) {
            // Handle the case where the specified ID does not exist in the collection
            return null;
        }
        Appointment appt = AppointmentConvertor.toAppointment(doc);
        return appt;
    }
 
    public List<Appointment> getList() {
        List<Appointment> appts = new ArrayList<Appointment>();
        MongoCursor<Document>  cursor = mongoCollectionUsers.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Appointment appt = AppointmentConvertor.toAppointment(doc);
                appts.add(appt);
            }
        } finally {
            cursor.close();
        }
        return appts;
    }
    
    public List<Appointment> getListByUsername(String username) {
    	List<Appointment> apptList = getList();
    	List<Appointment> appts = new ArrayList<>();
    	for(Appointment app: apptList) {
    		if(app.getUsername().equals(username)) {
    			appts.add(app);
    		}
    	}
    	 
        return appts;
    }
    
    public List<Appointment> getListByDoctorName(String username) {
    	List<Appointment> apptList = getList();
    	List<Appointment> appts = new ArrayList<>();
    	for(Appointment app: apptList) {
    		if(app.getDoctorUserName().equals(username)) {
    			appts.add(app);
    		}
    	}
    	 
        return appts;
    }
 

}
