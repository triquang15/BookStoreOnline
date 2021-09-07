<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage Customers - Evergreen BookStore Administration</title>
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src="../js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="page_heading">Customers Management</h2>
		<h3>
			<a href="new_customer">Create New Customer</a>
		</h3>
	</div>

	<c:if test="${message != null}">
		<div align="center">
			<h4 class="message">${message}</h4>
		</div>
	</c:if>

	<div align="center">
		<table cellpadding="5" border="1">

			<tr>
				<th>Index</th>
				<th>Id</th>
				<th>Email</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>City</th>
				<th>Country</th>
				<th>Registered Date</th>
				<th>Action</th>
			</tr>
			<c:forEach var="customer" items="${listCustomers}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${customer.customerId}</td>
					<td>${customer.email}</td>
					<td>${customer.firstname}</td>
					<td>${customer.lastname}</td>
					<td>${customer.city}</td>
					<td>${customer.countryName}</td>
					<td>${customer.registerDate}</td>
					<td><a href="edit_customer?id=${customer.customerId}">Edit</a>
						&nbsp; <a href="javascript:void(0);" class="deleteLink"
						id="${customer.customerId}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$(".deleteLink")
								.each(
										function() {
											$(this)
													.on(
															"click",
															function() {
																customerId = $(
																		this)
																		.attr(
																				"id");
																if (confirm('Are you sure to delete the customer with Id '
																		+ customerId
																		+ '?')) {
																	window.location = 'delete_customer?id='
																			+ customerId;
																}
															});
										});
					});
</script>
</html>