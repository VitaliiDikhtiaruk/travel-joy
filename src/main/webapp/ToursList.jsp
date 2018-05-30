<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Travel-joy Tours</title>
</head>
<body>
<form action="TourControllerServlet" method="post">
	<center>
		<h1>All available tours</h1>
        <h2>
        	<a href="TourForm.jsp">Add New Tour</a>
        	&nbsp;&nbsp;&nbsp;
        	<a href="TourControllerServlet">List All Tours</a>
        	
        	
        </h2>
        </center>
        <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Tours</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Date</th>
                <th>Countries</th>
          
            </tr>

            <c:forEach var="tour" items="${tours}">
                <tr>
                    <td><c:out value="${tour.id}" /></td>
                    <td><c:out value="${tour.name}" /></td>
                    <td><c:out value="${tour.description}" /></td>
                    <td><c:out value="${tour.price}" /></td>
                    <td><c:out value="${tour.date}" /></td>
                    <td><c:out value="${tour.countries}" /></td>
                    <td>
                    	<a href="edit?id=<c:out value='${tour.id}' />">Edit</a>
                    	&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="remove?id<c:out value='${tour.id}' />">Delete</a>                    	
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</form>
</body>
</html>