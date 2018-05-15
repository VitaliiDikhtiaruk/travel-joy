<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logged in</title>
</head>
<body>
	<table style="with: 50%">
		<tr>
			<td>
				<%--<% String login = request.getParametr("login"); --%> 
				<a> Welcome <% out.println("login"); %> ! You have logged in</a>
			</td>
		</tr>
		<tr></tr><tr><td></td><td></td><td><a href="LoginWelcome.jsp"><b>Logout</b></a></td></tr>
	</table>
</body>
</html>