<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Evergreen BookStore Administration</title>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="page_heading">Administrative Dashboard</h2>
	</div>
	
	<div align="center">
	<hr width = "60%">
		<h2 class="page_heading">Quick Actions:</h2>
		<b>
		<a href="create_book">New Book</a> &nbsp;
		<a href="create_user">New User</a> &nbsp;
		<a href="create_category">New Category</a> &nbsp;
		<a href="create_customer">New Customer</a> 
		</b>
	</div>
	
	<div align="center">
	<hr width = "60%">
		<h2 class="page_heading">Recent Sales:</h2>
	</div>
	
		<div align="center">
		<hr width = "60%">
		<h2 class="page_heading">Recent Reviews:</h2>
	</div>
	
	<div align="center">
	<hr width = "60%">
		<h2 class="page_heading">Statistics:</h2>
	</div>
	<hr width = "60%">
	<jsp:directive.include file="footer.jsp" />
</body>
</html>