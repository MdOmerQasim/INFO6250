<%@page import="java.util.List"%>
<%@page import="neu.edu.model.Appointment"%>
<%@page import="neu.edu.model.UserSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/styles.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<style>
/*         body {
            background-color: #f2f2f2;
        }
        form {
            width: 500px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 0 5px #ccc;
        }
        label, input[type="text"], select, textarea {
            display: block;
            width: 100%;
            margin-bottom: 10px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
} */

        
       @import url(https://fonts.googleapis.com/css?family=Roboto:300); 
       
    table {
   	font-family: "Roboto", sans-serif;
	border-collapse: collapse;
	background-color: #f2f2f2;
	width: 100%;
	}

	td, th {
		border: 1px solid #dddddd;
		text-align: left;
		padding: 8px;
	}
        
  
	.login-page {
	  width: 360px;
	  padding: 8% 0 0;
	  margin: auto;
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
	/* button {
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
	} */
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
	
td:first-child {
  display: none;
}

        
        
    </style>
    <link rel="icon" href="hospital-icon.png" type="image/icon type">
<title>WeCare | Appointments</title>
</head>
<body>

	<%
		UserSession userSession = (UserSession)session.getAttribute("userSession");	
	%>

	<ul>
      	<li class="center">WeCare | <% out.append(userSession.getUsername()); %></li>
      	<li><a href="logout">Logout</a></li>
      	<li><a href="controller?action=profile">Profile</a></li>
      	<li><a href="controller?action=viewAppointment">View Appointment</a></li>
		<li><a href="controller?action=dashboard">Dashboard</a></li>		
	</ul>
	
	<!-- <div class="btn-group btn-group-toggle" data-toggle="buttons">
	  <label class="btn btn-secondary active">
	    <input type="radio" name="options" id="option1" autocomplete="off" checked> Active
	  </label>
	  <label class="btn btn-secondary">
	    <input type="radio" name="options" id="option2" autocomplete="off"> Radio
	  </label>
	  <label class="btn btn-secondary">
	    <input type="radio" name="options" id="option3" autocomplete="off"> Radio
	  </label>
	</div> -->

    <!-- Add your table of data below -->
	<div style="padding: 50px">
		<table>
			<tr>
				<th>Username</th>
				<th>Email</th>
				<th>Patient Name</th>
				<th>Date</th>
				<th>Message</th>
				<th>Action</th>
			</tr>
			<%
			List<Appointment> apptList = (List<Appointment>) request.getAttribute("appointments");
			
			if(!apptList.isEmpty()) {
			for (Appointment appt : apptList) {
			%>
			<!-- Add more rows of data as needed -->
			<tr>
				<td><%=appt.getId()%></td>
				<td><%=appt%></td>
				<td><%=appt.getEmail()%></td>
				<td><%=appt.getPatientName()%></td>
				<td><%=appt.getApptDate()%></td>
				<td><%=appt.getMessage()%></td>
				
				
				
				
				<%
      			if(appt.getDoctorNote().equals("Awaiting Doctor review")) {
      			%>
      			<td><button class="btn btn-primary" onclick="viewRow(this)">View</button></td>     		
      			<% 
      			} else {
      			%>
				<!-- <td><p>Reviewed!</p></td>  --> 
				<td><%=appt.getDoctorNote()%></td> 
				<% }
				%>
			</tr>			
			<%
		} 
	} else {
		// display no data message
		%>
		<tr>
			<td></td>
			<td colspan="7">No data found.</td>
		</tr>
	<% } %>
			
			
		</table>
	</div>
	
	<script type="text/javascript">
	 function viewRow(button) {
	        var row = button.parentNode.parentNode;
	        var cells = row.getElementsByTagName("td");
	        var id = cells[0].innerHTML;       	
	        var xhr = new XMLHttpRequest();
            xhr.open("POST", "controller?action=viewPatientData", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
              if (xhr.readyState === 4 && xhr.status === 200) {    	  
                  window.location.href = "controller?action=viewPatient"    
              } 
            };  
           xhr.send("id=" + id);
	 }
	
	</script>
    
   
    
</body>
</html>
