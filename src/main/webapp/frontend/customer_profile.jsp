<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Profile - Online BookStore </title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>

	<jsp:directive.include file="header.jsp" />


	<div align="center">
		<h2>Welcome, ${loggedCustomer.fullname}</h2>

	
	<table class="normal">
		<tr>
			<td><b>Email:</b></td>
			<td>${loggedCustomer.email}</td>
		</tr>
		<tr>
			<td><b>Full Name:</b></td>
			<td>${loggedCustomer.fullname}</td>
		</tr>
		<tr>
			<td><b>Phone:</b></td>
			<td>${loggedCustomer.phone}</td>
		</tr>
		<tr>
			<td><b>Address:</b></td>
			<td>${loggedCustomer.addressLine1}</td>
		</tr>
		<tr>
			<td><b>Zip Code:</b></td>
			<td>${loggedCustomer.zipcode}</td>
		</tr>
		<tr>
			<td><b>State:</b></td>
			<td>${loggedCustomer.state}</td>
		</tr>
		<tr>
			<td><b>Country:</b></td>
			<td>${loggedCustomer.country}</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td colspan="2" align="center"><b><a href="edit_profile">Edit My Profile</a></b></td></tr>
	</table>
	</div>
	<jsp:directive.include file="footer.jsp" />
</body>
</html>