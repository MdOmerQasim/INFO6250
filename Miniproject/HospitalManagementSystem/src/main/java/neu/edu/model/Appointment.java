package neu.edu.model;

public class Appointment {
	
	private String id;
	private String username;
	private String patientName;
	private String email;
	private String phoneNum;
	private String doctorName;
	private String doctorUserName;
	private String apptDate;
	private String message;
	private String doctorNote;
	
	
	public Appointment() {
		// TODO Auto-generated constructor stub
	}
	

	public Appointment(String username, String patientName, String email, String phoneNum, String doctorName,
			String doctorUserName, String apptDate, String message, String doctorNote) {
		super();
		this.username = username;
		this.patientName = patientName;
		this.email = email;
		this.phoneNum = phoneNum;
		this.doctorName = doctorName;
		this.doctorUserName = doctorUserName;
		this.apptDate = apptDate;
		this.message = message;
		this.doctorNote = doctorNote;
	}

	
	public String getDoctorNote() {
		return doctorNote;
	}
	public void setDoctorNote(String doctorNote) {
		this.doctorNote = doctorNote;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDoctorUserName() {
		return doctorUserName;
	}
	public void setDoctorUserName(String doctorUserName) {
		this.doctorUserName = doctorUserName;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getApptDate() {
		return apptDate;
	}
	public void setApptDate(String apptDate) {
		this.apptDate = apptDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return username;
	}
	
	
	
}
