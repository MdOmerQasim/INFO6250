<%@ page import="java.util.List, java.util.ArrayList" %>
<%@page import="neu.edu.model.UserSession"%>
<%@page import="javax.servlet.http.HttpSession"%> 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/styles.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<style>
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

h1{
text-align: center;
 margin-top: 50px;
}

        
        
    </style>
    <link rel="icon" href="hospital-icon.png" type="image/icon type">
<title>WeCare | Book</title>
</head>
<body>

	<%
		UserSession userSession = (UserSession)session.getAttribute("userSession");	
	%>
	
	<ul>
      	<li class="center">WeCare | <% out.append(userSession.getUsername()); %></li>
      	<li><a href="logout">Logout</a></li>
      	<li><a href="controller?action=profile">Profile</a></li>
      	<li><a href="controller?action=bookAppointment">Book Appointment</a></li>
      	<li><a href="controller?action=viewAppointment">View Appointment</a></li>
		<li><a href="controller?action=dashboard">Dashboard</a></li>		
	</ul>

    <h1>Book an Appointment</h1>
     <div class="login-page">
     <div class="form">
    <form action="userBookAppointment" method="post">
 		 <input type="text" id="name" name="name" placeholder="username" value="<%= ((UserSession)session.getAttribute("userSession")).getUsername()%>" disabled>
        <input type="email" id="email" name="email" placeholder="email" value="<%= ((UserSession)session.getAttribute("userSession")).getEmail()%>" disabled>
        <!-- <input type="tel" id="phone" name="phone" placeholder="phone number" required> -->
		<!-- <input type="tel" id="phone" name="phone" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" placeholder="phone number" disabled>
	      --> 
        <select id="doctor" name="doctor" required>
    		<!-- <option value="">--- Select a doctor ---</option> -->
    		<%
        	List<String> doctorNames = (List<String>) request.getAttribute("doctorNames");
        	for (String doctorName : doctorNames) {
            	%>
<%--             	<option value="<%=doctorName%>"><%=doctorName%></option> --%>
            	<option value="<%=doctorName%>">Dr. <%=doctorName%></option>
            	<% 
        	}
    		%>
		</select>

        <input title="Select date" type="date" id="date" name="date" placeholder="YYYY-MM-DD" required>

        <textarea title="Enter description" id="message" name="message" placeholder="message"></textarea>
       <!--  <input type="submit" value="Book Appointment"> -->
        <button>Book Appointment</button>
    </form>
    </div>
	</div>
	<script>
		var dateInput = document.getElementById("date");
	
		// Disable past dates
		var today = new Date();
		var minDate = today.toISOString().slice(0,10);
		dateInput.setAttribute("min", minDate);
	
		// Disable Saturdays and Sundays and specific dates
		function disableDates(date) {
		  // Disable weekends
		  if (date.getDay() === 0 || date.getDay() === 6) {
		    return true;
		  }
		  
		  // Disable specific dates
		  var disabledDates = ["2023-02-25", "2023-02-28", "2023-03-02"];
		  var dateString = date.toISOString().slice(0,10);
		  if (disabledDates.indexOf(dateString) !== -1) {
		    return true;
		  }
		  
		  // Disable past dates
		  if (date < today) {
		    return true;
		  }
		  
		  // Enable all other dates
		  return false;
		}
	
		flatpickr(dateInput, {
		  disable: [
		    disableDates
		  ]
		});
		
		//Phone
		  const phoneInput = document.getElementById('phone');
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
    </script>
</body>
</html>
