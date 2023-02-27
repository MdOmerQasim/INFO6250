<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/styles.css" >
<style type="text/css">
@import url(https://fonts.googleapis.com/css?family=Roboto:300);


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
</style>
<link rel="icon" href="hospital-icon.png" type="image/icon type">
<title>WeCare | Register</title>
</head>
<body>

	<ul>
		<li class="center">WeCare</li>
		<li><a href="index.html">Home</a></li>
	</ul>
  
    <div class="login-page">
    	<!-- Invalid user credentials -->
		<%	if(request.getAttribute("errorMsg")!= null){ %>
	    <p class="error"> <%=request.getAttribute("errorMsg")%> </p>     		
	    <%	}  %>
	  <div class="form">
	    <form action="registration" method="post">
	      <input title="Enter full name" type="text" id="firstname" name="firstname" placeholder="full name" required/>
	      <input title="Enter username" type="text" id="username" name="username" placeholder="username" required/>
	      <input title="Enter password" type="password" id="password" name="password" placeholder="password" required/>
	      <input title="Enter email" type="text" id="email" name="email" placeholder="email address" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}">
	      
	      <!-- <input type="text" id="lastname" name="lastname" placeholder="last name" required/>  -->     
	      <input type="tel" id="lastname" name="lastname" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" placeholder="phone number" required>
	      
	      <button>create</button>
	      <p class="message">Already registered? <a href="login">Sign In</a></p>
	    </form>
	  </div>
	</div>


<script>
  //Username
  const usernameInput = document.getElementById("username");
	usernameInput.addEventListener("input", function() {
	  const usernameValue = usernameInput.value;
	  if (!/^[a-zA-Z_-]+$/.test(usernameValue)) {
		  usernameInput.classList.add("invalid");
	    } else {
	      usernameInput.classList.remove("invalid");
	    }
	});

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
