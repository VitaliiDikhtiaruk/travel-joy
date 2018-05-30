<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log in</title>
</head>
<body>
<form action="Login" method="post">
	<tr>
			</td>
			<td></td>
		</tr>
	<center>
		<h1>Log in Form</h1>
		<form action="login" method="post">
			<table style="with: 50%">
				<tr>
					<td>User login</td>
					<td><input type="text" name="login" /></td>
				</tr>
				<tr>
					<td>User Password</td>
					<td><input type="password" name="password" /></td>
				</tr>
			</table>
			<input type="submit" name="submit" value="login" />
				<td><a href="RegistrationForm.jsp">Registration</a></td>
		</form>
	</center>
</body>
</html>