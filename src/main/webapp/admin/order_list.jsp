<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage Orders - Evergreen BookStore Administration</title>
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src="../js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="page_heading">Book Orders Management</h2>

	</div>

	<c:if test="${message != null}">
		<div align="center">
			<h4 class=message>${message}</h4>
		</div>
	</c:if>

	<div align="center">
		<table cellpadding="5" border="1">

			<tr>
				<th>Index</th>
				<th>Order Id</th>
				<th>Ordered by</th>
				<th>Book Copies</th>
				<th>Total</th>
				<th>Payment method</th>
				<th>Status</th>
				<th>Order Date</th>
				<th>Action</th>
			</tr>
			<c:forEach var="order" items="${listOrder}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${order.orderId}</td>
					<td>${order.customer.fullname}</td>
					<td>${order.bookCopies}</td>
					<td>$<fmt:formatNumber value="${order.total}"></fmt:formatNumber></td>
					<td>${order.paymentMethod}</td>
					<td>${order.status}</td>
					<td>${order.orderDate}</td>
					
				
					<td>
					<a href="view_order?id=${order.orderId}">Details</a>
					<a href="edit_order?id=${order.orderId}">Edit</a>
					 &nbsp; <a
						href="javascript:void(0);" class="deleteLink" id="${order.orderId}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>

<script >
$(document).ready(function () {
	$(".deleteLink").each(function() {
		$(this).on("click", function() {
			orderId = $(this).attr("id");
			if(confirm('Are you sure to delete the order with Id ' + orderId + '?')) {
				window.location = 'delete_order?id=' + orderId;
				}
			});
		});
});
</script>
</html>