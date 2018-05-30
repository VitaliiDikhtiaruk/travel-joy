<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration travel-joy</title>
</head>
<body>
	<center>

		<h1>Registration form</h1>
		<form action="LoginRegister" method="post">
			<table style="with: 50%">
				<tr>
					<td>First Name</td>
					<td><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td><input type="text" name="lastname" /></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="email" name="email" /></td>
				</tr>
				<tr>
					<td>Date of Birth</td>
					<!-- 	<td>
			<fmt:formatDate var="dob" value="dob" pattern="yyyymmdd"/>
			<input type="text" name="dob" />
			</td>
			 -->
					<td><input type="date" name="dob" /></td>

				</tr>
				<tr>
					<td>Login</td>
					<td><input type="text" name="login" /></td>
				</tr>
				<tr>

					<td>Password</td>

					<td>
					<!-- <input
						title="Password must contain at least 8 characters, including UPPER/lowercase and numbers"
						type="password" required
						pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" name="password" />
						-->
<input id="password" name="password" type="password" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Password must contain at least 8 characters, including UPPER/lowercase and numbers' : ''); if(this.checkValidity()) form.password_two.pattern = this.value;" placeholder="Password" required>
</td>
				</tr>
				<tr>
					<td>Confirm Password</td>
					<td>
<input id="password_two" name="password2" type="password" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');" placeholder="Verify Password" required>
					</td>
					
				</tr>
			</table>
			<input type="submit" name="submit" value="register" />

		</form>
	</center>
</body>
</html>