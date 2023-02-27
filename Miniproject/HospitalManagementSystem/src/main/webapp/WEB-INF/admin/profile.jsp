<%@page import="neu.edu.model.UserSession"%>
<%@page import="javax.servlet.http.HttpSession"%> 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/styles.css">
<style type="text/css">
/* Add your registration form styling here */
@import url(https://fonts.googleapis.com/css?family=Roboto:300);


	.login-page {
	  width: 360px;
	  padding: 8% 0 0;
	  margin: auto;
	  padding-top: 50px;
	}
	.form {
	  position: relative;
	  z-index: 1;
	  background: #FFFFFF;
	  max-width: 360px;
	  margin: 0 auto 100px;
	  padding: 45px;
	  text-align: center;
	  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
	}
	.form input {
	  font-family: "Roboto", sans-serif;
	  outline: 0;
	  background: #f2f2f2;
	  width: 100%;
	  border: 0;
	  margin: 0 0 15px;
	  padding: 15px;
	  box-sizing: border-box;
	  font-size: 14px;
	}
	
	select, textarea {
	font-family: "Roboto", sans-serif;
	  outline: 0;
	  background: #f2f2f2;
	  width: 100%;
	  border: 0;
	  margin: 0 0 15px;
	  padding: 15px;
	  box-sizing: border-box;
	  font-size: 14px;
	}
	
	.form button {
	  font-family: "Roboto", sans-serif;
	  text-transform: uppercase;
	  outline: 0;
	  background: #4CAF50;
	  width: 100%;
	  border: 0;
	  padding: 15px;
	  color: #FFFFFF;
	  font-size: 14px;
	  -webkit-transition: all 0.3 ease;
	  transition: all 0.3 ease;
	  cursor: pointer;
	}
	.form button:hover,.form button:active,.form button:focus {
	  background: #43A047;
	}
	.form .message {
	  margin: 15px 0 0;
	  color: #b3b3b3;
	  font-size: 12px;
	}
	.form .message a {
	  color: #4CAF50;
	  text-decoration: none;
	}
	.form .register-form {
	  display: none;
	}
	.container {
	  position: relative;
	  z-index: 1;
	  max-width: 300px;
	  margin: 0 auto;
	}
	.container:before, .container:after {
	  content: "";
	  display: block;
	  clear: both;
	}
	.container .info {
	  margin: 50px auto;
	  text-align: center;
	}
	.container .info h1 {
	  margin: 0 0 15px;
	  padding: 0;
	  font-size: 36px;
	  font-weight: 300;
	  color: #1a1a1a;
	}
	.container .info span {
	  color: #4d4d4d;
	  font-size: 12px;
	}
	.container .info span a {
	  color: #000000;
	  text-decoration: none;
	}
	.container .info span .fa {
	  color: #EF3B3A;
	}
	.error {
        color: red;
        font-weight: bold;
        text-align: center;
        padding: 10px;
      }
	body {
	  background: rgb(255, 204, 204);
	  font-family: "Roboto", sans-serif;
	  -webkit-font-smoothing: antialiased;
	  -moz-osx-font-smoothing: grayscale;      
	}
        
     input.invalid {
  border: 2px solid red;
}   

.error {
        color: red;
        font-weight: bold;
        text-align: center;
        padding: 10px;
      }
      
.success {
        color: green;
        font-weight: bold;
        text-align: center;
        padding: 10px;
      }
      
      .alignleft {
       padding-left: 300px;
    float: left;
}
.alignright {
padding-right: 300px;
    float: right;
}

input.invalid {
  border: 2px solid red;
}




/* .registration-form {
	background-color: #f2f2f2;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0px 0px 10px 0px #ccc;
	margin: 50px auto;
	width: 500px;
}

.form-control {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 5px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical;
}

input[type=submit] {
	background-color: #4CAF50;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

input[type=submit]:hover {
	background-color: #45a049;
} */
</style>
<link rel="icon" href="hospital-icon.png" type="image/icon type">
<title>WeCare | Profile</title>
</head>
<body>

	<%
		UserSession userSession = (UserSession)session.getAttribute("userSession");	
	%>
	<ul>
		<li class="center">WeCare | <% out.append(userSession.getUsername()); %></li>
		<li><a href="logout">Logout</a></li>
      	<li><a href="controller?action=profile">Profile</a></li>
      	<li><a href="controller?action=viewAppointment">View Appointments</a></li>
      	<li><a href="controller?action=viewUsers">View Users</a></li>
		<li><a href="controller?action=dashboard">Dashboard</a></li>	
	</ul>

	<!-- Add your registration form below -->
	<div class="login-page alignleft">
		<h2>User Profile</h2>
	    <div class="form">
		<form action="adminProfile?action=editProfile" method="post">
			<%-- <input type="text" id="username" name="username" class="form-control" value="${requestScope.username}" disabled> 
			<input type="email" id="email" name="email" class="form-control" value="${requestScope['email']}" required>  --%>
			<input title="Enter full name" type="text" id="firstname" name="firstname" placeholder="full name" value="<%= session.getAttribute("fullName") %>" required/>
			
			<input title="Cannot change username (Contact admin)" type="text" id="username" name="username" class="form-control" value="<%= request.getAttribute("username")%>" disabled> 
			<input title="Enter email" title="Enter email" type="email" id="email" name="email" class="form-control" value="<%= session.getAttribute("email")%>" required> 		
			<input title="Enter phone number" type="tel" id="lastname" name="lastname" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" placeholder="phone number" value="<%= session.getAttribute("telNum") %>" required>
	      
			<input type="hidden" name="page" value="editProfile" /> 
			<!-- <input type="submit" value="Submit"> -->
			<button>Update</button>
		</form>
		</div>
		<% if(request.getAttribute("successMsg")!= null){ %>		
      	<p class="success"><%=request.getAttribute("successMsg")%></p>
	    <% } %>
	</div>

	<div class="login-page alignright">
		<h2>Update Password</h2>
		<div class="form">
		<form action="adminProfile?action=editProfilePassword" method="post">
			<input title="Enter password" type="password" id="password" name="password" class="form-control" value="" placeholder="password" required>
			<input title="Re-enter password" type="password" id="confirm_password" name="confirm_password" class="form-control" value="" placeholder="confirm password" required> 
			<input type="hidden" name="page" value="editProfilePassword" /> 
			<!-- <input type="submit" value="Submit"> -->
			<button>Update</button>
		</form>
		</div>
		<% if(request.getAttribute("errorMsg")!= null){ %>		
      	<p class="error"><%=request.getAttribute("errorMsg")%></p>
	    <% } %>
	    <% if(request.getAttribute("successPasswordMsg")!= null){ %>		
      	<p class="success"><%=request.getAttribute("successPasswordMsg")%></p>
	    <% } %>
	</div>
<script>
  //Phone
  const phoneInput = document.getElementById('lastname');
  phoneInput.addEventListener('input', () => {
    const phoneNumber = phoneInput.value.replace(/[^\d\w]/g, '');
    if (phoneNumber.length > 10) {
      phoneInput.value = phoneNumber.slice(0, 10);
    }
    const formattedPhoneNumber = phoneInput.value.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
    phoneInput.value = formattedPhoneNumber;
    if (!phoneInput.checkValidity()) {
      phoneInput.classList.add("invalid");
    } else {
      phoneInput.classList.remove("invalid");
    }
  });
  
  //Email
  const emailInput = document.getElementById("email");
  emailInput.addEventListener("input", function() {
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const emailValue = emailInput.value;

    if (!emailInput.checkValidity()) {
    	emailInput.classList.add("invalid");
      } else {
    	emailInput.classList.remove("invalid");
      }
  });
</script>
</body>
</html>
