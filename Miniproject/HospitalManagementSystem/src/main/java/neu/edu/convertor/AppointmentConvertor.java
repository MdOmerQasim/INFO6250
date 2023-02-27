package neu.edu.convertor;

import org.bson.Document;
import org.bson.types.ObjectId;

import neu.edu.model.Appointment;

public class AppointmentConvertor {

	public static Document toDocument(Appointment appt) {

		Document doc = new Document(
						"username", appt.getUsername())
				.append("patient_name", appt.getPatientName())
				.append("email", appt.getEmail())
				.append("phone_number", appt.getPhoneNum())
				.append("doctor_name", appt.getDoctorName())
				.append("doctor_username", appt.getDoctorUserName())
				.append("appointment_date", appt.getApptDate())
				.append("Message", appt.getMessage())
				.append("doctor_note", appt.getDoctorNote()		
						);
		return doc;

	}


	public static Appointment toAppointment(Document apptDoc) {
		Appointment appt = new Appointment();
		appt.setId(((ObjectId) apptDoc.get("_id")).toString());
		appt.setUsername(apptDoc.getString("username"));
		appt.setPatientName(apptDoc.getString("patient_name"));
		appt.setEmail(apptDoc.getString("email"));
		appt.setPhoneNum(apptDoc.getString("phone_number"));
		appt.setDoctorName(apptDoc.getString("doctor_name"));
		appt.setDoctorUserName(apptDoc.getString("doctor_username"));
		appt.setApptDate(apptDoc.getString("appointment_date"));
		appt.setMessage(apptDoc.getString("Message"));
		appt.setDoctorNote(apptDoc.getString("doctor_note"));
		return appt;
	}
	
}
