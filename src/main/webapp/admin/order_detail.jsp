<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Order Details - Evergreen Bookstore Administration</title>
	<link rel="stylesheet" href="../css/style.css" >
	<script type="text/javascript" src="../js/jquery-3.6.0.js"></script>	
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2 class="pageheading">Details of Order ID: ${order.orderId}</h2>		
	</div>
	
	<c:if test="${message != null}">
	<div align="center">
		<h4 class="message">${message}</h4>
	</div>
	</c:if>
	
	<div align="center">
		<h2>Order Overview:</h2>	
		<table>
			<tr>
				<td><b>Ordered By: </b></td>
				<td>${order.customer.fullname}</td>
			</tr>
			<tr>
				<td><b>Book Copies: </b></td>
				<td>${order.bookCopies}</td>
			</tr>
			<tr>
				<td><b>Total Amount: </b></td>
				<td>$<fmt:formatNumber value="${order.total}"  /></td>
			</tr>			
			<tr>
				<td><b>Recipient Name: </b></td>
				<td>${order.firstname}</td>
			</tr>
			<tr>
				<td><b>Recipient Phone: </b></td>
				<td>${order.phone}</td>
			</tr>
			<tr>
				<td><b>Payment Method: </b></td>
				<td>${order.paymentMethod}</td>
			</tr>
			<tr>
				<td><b>Shipping Address: </b></td>
				<td>${order.addressLine1}</td>
			</tr>
			<tr>
				<td><b>Order Status: </b></td>
				<td>${order.status}</td>
			</tr>
				<tr>
				<td><b>Order City: </b></td>
				<td>${order.city}</td>
			</tr>
				<tr>
				<td><b>Country: </b></td>
				<td>${order.countryName}</td>
			</tr>
				<tr>
				<td><b>Zipcode: </b></td>
				<td>${order.zipcode}</td>
			</tr>
			<tr>
				<td><b>Order Date: </b></td>
				<td>${order.orderDate}</td>
			</tr>												
		</table>
	</div>
	<div align="center">
		<h2>Ordered Books</h2>
		<table border="1">
			<tr>
				<th>Index</th>
				<th>Book Title</th>
				<th>Author</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Subtotal</thd>
			</tr>
			<c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>
					<img style="vertical-align: middle;" src="data:image/jpg;base64,${orderDetail.book.base64Image}" width="48" height="64" />
					${orderDetail.book.title}
				</td>
				<td>${orderDetail.book.author}</td>
				<td>$<fmt:formatNumber value="${orderDetail.book.price}"  /></td>
				<td>${orderDetail.quantity}</td>
				<td>$<fmt:formatNumber value="${orderDetail.subtotal}" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="6" align="right">
				<p>Subtotal: $<fmt:formatNumber value="${order.subtotal}" /></p>
				<p>Tax: $<fmt:formatNumber value="${order.tax}" /></p>
				<p>Shipping Fee: $<fmt:formatNumber value="${order.shippingFee}" /></p>
					<p>TOTAL: $<fmt:formatNumber value="${order.total}" /></p>
				</td>
			</tr>
		</table>
	</div>
	<div align="center">
		<br/>
		<a href="edit_order?id=${order.orderId}">Edit this Order</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0);" class="deleteLink" id="${order.orderId}">Delete this Order</a>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
	
	<script>
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					orderId = $(this).attr("id");
					if (confirm('Are you sure you want to delete the order with ID ' +  orderId + '?')) {
						window.location = 'delete_order?id=' + orderId;
					}					
				});
			});
		});	
	</script>
</body>
</html>