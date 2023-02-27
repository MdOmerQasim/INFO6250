<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="neu.edu.model.Appointment"%> 
<%@page import="javax.servlet.http.HttpSession"%> 
<%@page import="neu.edu.model.UserSession"%>
<html>
<head>
<link rel="stylesheet" href="css/styles.css" >
    <title>View Patient Data</title>
    <style type="text/css">
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
	input {
	  font-family: "Roboto", sans-serif;
	  outline: 0;
	  background: #white;
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
	button {
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
	
</style>
<link rel="icon" href="hospital-icon.png" type="image/icon type">
<title>WeCare | Patient</title>
</head>
<body>

	<%
		UserSession userSession = (UserSession)session.getAttribute("userSession");	
	%>
	<ul>
		<li><a href="controller?action=viewAppointment">Back</a></li>
	</ul>
    <h1>Appointment Details</h1>
    <form action="doctorViewPatient?action=apptSubmit" method="post">
    <%
      
      	if(request.getAttribute("successMsg")!= null){
      		%>
      		
      <p class="success"><%=request.getAttribute("successMsg")%></p>
      		
      		<% 
      	}
      %>
    
    <table>
   	<% if (session.getAttribute("appt") != null) { %>
        <tr>
            <td>Patient Name</td>
            <td><%= ((Appointment)session.getAttribute("appt")).getPatientName() %></td>
        </tr>
        <tr>
            <td>Doctor Name</td>
            <td><%= ((Appointment)session.getAttribute("appt")).getDoctorName() %></td>
        </tr>
        <tr>
            <td>Date</td>
            <td><%= ((Appointment)session.getAttribute("appt")).getApptDate() %></td>
        </tr>
        <%-- <tr>
            <td>Notes:</td>
            <td><%= ((Appointment)session.getAttribute("appt")).getDoctorNote() %></td>
        </tr>  --%>
        
        
        <%
      		if(((Appointment)session.getAttribute("appt")).getDoctorNote().equals("Awaiting Doctor review")) {
      			%>
      			<tr>
            <td>Notes</td>
            <td><input type="text" name="notes" placeholder="describe patient encounter" required></td>
        </tr>    		
      			<% 
      			} else {
      			%>
				<tr>
            <td>Notes</td>
            <td><%=((Appointment)session.getAttribute("appt")).getDoctorNote()%></td>
        </tr>
				<% }
				%>
        
    <% } else { %>
        <tr>
            <td colspan="2">No appointment data found.</td>
        </tr>
    <% } %>
    </table>
    <!-- <input type="submit" value="Submit"> -->
    <button>Review</button>
	</form>
</body>
</html>

