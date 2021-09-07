<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Orders History- Evergreen BookStore </title>
<link rel="stylesheet" href="css/style.css">

</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="page_heading">My Orders History</h2>

	</div>

	<c:if test="${fn:length(listOrders) == 0}">
	<div align="center">
		<h3>You have not places any orders.</h3>
		</div>
	</c:if>
	<c:if test="${fn:length(listOrders) > 0}">
		<div align="center">
			<table cellpadding="5" border="1">

				<tr>
					<th>Index</th>
					<th>Order Id</th>
					<th>Quantity</th>
					<th>Total Amount</th>
					<th>Order Date</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
				<c:forEach var="order" items="${listOrders}" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${order.orderId}</td>
						<td>${order.bookCopies}</td>
						<td>$<fmt:formatNumber value="${order.total}" type="currency" ></fmt:formatNumber></td>
						<td>${order.orderDate}</td>
						<td>${order.status}</td>

						<td><a href="show_order_detail?id=${order.orderId}">View Details</a>
						</td>
					</tr>
				</c:forEach>

			</table>
		</div>
	</c:if>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>