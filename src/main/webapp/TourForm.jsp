<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Adding new tour</title>
</head>
<body>
<form action="LoginRegister" method="post">
	<center>
	
		<h1>Tours Management</h1>
        <h2>
        	
        	<a href="TourForm.jsp">Add New Tour</a>
        	&nbsp;&nbsp;&nbsp;
        	
        	<a href="ToursList.jsp">List All Tours</a>
        	
        </h2>
	</center>
    <div align="center">
		<c:if test="${tour != null}">
			<form action="update" method="post">
        </c:if>
        <c:if test="${tour == null}">
			<form action="insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
            	<h2>
            		<c:if test="${tour != null}">
            			Edit Tour
            		</c:if>
            		<c:if test="${tour == null}">
            			Add New Tour
            		</c:if>
            	</h2>
            </caption>
        		<c:if test="${tour != null}">
        			<input type="hidden" name="id" value="<c:out value='${tour.id}' />" />
        		</c:if>            
            <tr>
                <th>Name: </th>
                <td>
                	<input type="text" name="name" size="45"
                			value="<c:out value='${tour.name}' />"
                		/>
                </td>
            </tr>
            <tr>
                <th>Description: </th>
                <td>
                	<input type="text" name="description" size="45"
                			value="<c:out value='${tour.description}' />"
                	/>
                </td>
            </tr>
            <tr>
                <th>Price: </th>
                <td>
                	<input type="text" name="price" size="45"
                			value="<c:out value='${tour.price}' />"
                	/>
                </td>
            </tr>
            <tr>
                <th>Date: </th>
                <td>
                	<input type="text" name="date" size="45"
                			value="<c:out value='${tour.date}' />"
                	/>
                </td>
            </tr>
            <tr>
                <th>Countries: </th>
                <td>
                	<input type="text" name="countries" size="45"
                			value="<c:out value='${tour.countries}' />"
                	/>
                </td>
            </tr>
            <tr>
            	<td colspan="2" align="center">
            		<input type="submit" value="Save" name="add"/>
            	</td>
            </tr>
        </table>
        </form>
    </div>	
</body>
</html>