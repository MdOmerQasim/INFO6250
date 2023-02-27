package neu.edu.model;

public class UserRegistration {
	private String username;
	private String password;
	private String email;
	private Role role;
	private String firstname;
	private String lastname;

	public enum Role {

		ADMIN("admin"), USER("user"), DOCTOR("doctor");

		private final String roleName;

		// private enum constructor
		private Role(String roleName) {
			this.roleName = roleName;
		}
		
		public String getRoleName() {
			return roleName;
		}

	}

	public UserRegistration() {
		// TODO Auto-generated constructor stub
	}

	public UserRegistration(String username, String password, String email, Role role, String firstname, String lastname) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public UserRegistration(String username, String email, String role, String firstname, String lastname) {
		super();
		this.username = username;
		this.email = email;
		if("admin".equalsIgnoreCase(role)) {
			this.role = Role.ADMIN;
		}else if("user".equalsIgnoreCase(role)) {
			this.role = Role.USER;
		} else if("doctor".equalsIgnoreCase(role)) {
			this.role = Role.DOCTOR;
		}
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public UserRegistration(String username, String email, String role) {

		this.username = username;
		this.email = email;
		if("admin".equalsIgnoreCase(role)) {
			this.role = Role.ADMIN;
		}else if("user".equalsIgnoreCase(role)) {
			this.role = Role.USER;
		} else if("doctor".equalsIgnoreCase(role)) {
			this.role = Role.DOCTOR;
		}
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(String role) {
		if("admin".equalsIgnoreCase(role)) {
			this.role = Role.ADMIN;
		}else if("user".equalsIgnoreCase(role)) {
			this.role = Role.USER;
		} else if("doctor".equalsIgnoreCase(role)) {
			this.role = Role.DOCTOR;
		}
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	

}
