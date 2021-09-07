<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> Review Posted</title>
<link rel="stylesheet" href="css/style.css">

</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div align="center">
		
			<table class="normal" width="60%">
				<tr>
					<td><h2>Your Reviews</h2></td>
					<td>&nbsp;</td>
					<td><h2>${loggedCustomer.fullname}</h2></td>
				</tr>
				<tr>
					<td colspan="3"><hr></td>
				</tr>
				<tr>
					<td><span id="book-title">${book.title}</span><br> <img
						class="book-large" src="data:image/jpg;base64,${book.base64Image}" />
					</td>
					<td colspan="2">Your review has been posted. Thank you!</td>
				</tr>
				
			</table>
		
	</div>
	<jsp:directive.include file="footer.jsp" />
</body>

</html>