<%@page import="java.util.ArrayList"%>
<%@page import="neu.edu.model.UserRegistration"%>
<%@page import="neu.edu.model.UserSession"%>
<%@page import="neu.edu.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style type="text/css">

/* Add your table styling below */
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
</style>
<link rel="icon" href="hospital-icon.png" type="image/icon type">
<title>WeCare | View</title>
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
		<li><a href="adminDashboard">Dashboard</a></li>
	</ul>

	<% if(request.getAttribute("deleteFlag")!= null){ %>  		
    <p class="error"><%=request.getAttribute("deleteFlag")%></p>
    <% } %>
      
    <% if(request.getAttribute("updateFlag")!= null){ %>
    <p class="error"><%=request.getAttribute("updateFlag")%></p>
	<% } %>

	<!-- Add your table of data below -->
	<div style="padding: 50px">
		
		<button class="btn btn-success" onclick="addRow(this)">
		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-plus-fill" viewBox="0 0 16 16">
		  <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
		  <path fill-rule="evenodd" d="M13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>
		</svg>
		Add</button> <br></br>
		<table>
			<tr>
				<th>Full name</th>
				<th>Username</th>
				<th>Email</th>
				<th>Phone number</th>
				<th>Role</th>
				<th>Action</th>

			</tr>
			<%
			ArrayList<UserRegistration> userRegistrations = (ArrayList<UserRegistration>) session.getAttribute("userRegistrations");
			
			if(!userRegistrations.isEmpty()) {
			
			for (UserRegistration userRegistration : userRegistrations) {
				if(!userRegistration.getUsername().equals(userSession.getUsername())) {
			%>
			<!-- Add more rows of data as needed -->
			<tr>
				<td><%=userRegistration.getFirstname()%></td>
				<td><%=userRegistration.getUsername()%></td>
				<td><%=userRegistration.getEmail()%></td>
				<td><%=userRegistration.getLastname()%></td>
				<td><%=userRegistration.getRole()%></td>
				<td>
					<button class="btn btn-primary" onclick="editRow(this)">
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
					  <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
					  <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
					</svg>	
					Edit</button>
					<button class="btn btn-danger" onclick="deleteRow(this)">
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3-fill" viewBox="0 0 16 16">
					  <path d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5Zm-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5ZM4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06Zm6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528ZM8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5Z"/>
					</svg>
					Delete</button>
				</td>

			</tr>

			<%
			} } }else {
			%>
			<tr><td colspan="4">No Data Found</td></tr>
			<% } %>
			
		</table>
	</div>

	<script type="text/javascript">
 	
 	function addRow(button) {
       /*  var row = button.parentNode.parentNode;
        var cells = row.getElementsByTagName("td");
        var username = cells[0].innerHTML; */
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "controller?action=addUserData", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {    	  
              window.location.href = "controller?action=addUser"    
          } 
        };  
       xhr.send("username=" + "add");
 	}
 	
 	function editRow(button) {
        var row = button.parentNode.parentNode;
        var cells = row.getElementsByTagName("td");
        var username = cells[1].innerHTML;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "controller?action=editUserData", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {    	  
              window.location.href = "controller?action=editUser"    
          } 
        };  
       xhr.send("username=" + username);
 	}
 	
 	function deleteRow(button) {
        var row = button.parentNode.parentNode;
        var cells = row.getElementsByTagName("td");
        var username = cells[1].innerHTML;
        
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "controller?action=deleteUserData", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {    	  
              window.location.href = "controller?action=deleteUser"    
          } 
        };  
       xhr.send("username=" + username);
 	}
 	
 	
 
	</script>

</body>
</html>
