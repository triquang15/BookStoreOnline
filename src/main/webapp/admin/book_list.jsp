<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage Books - Evergreen BookStore Administration</title>
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src="../js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="page_heading">Books Management</h2>
		<h3>
			<a href="new_book">Create New Book</a>
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
				<th>Image</th>
				<th>Author</th>
				<th>Title</th>
				<th>Category</th>
				<th>Price</th>
				<th>Last Updated</th>
				<th>Action</th>
			</tr>
			<c:forEach var="book" items="${listBooks}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${book.bookId}</td>
					
					<td>
						<img alt="book" src="data:image/jpg;base64,${book.base64Image}" width="84", height="110"/>
					</td>				
					<td>${book.author}</td>
					<td>${book.title}</td>
					
					<td>${book.category.name}</td>
					<td>$${book.price}</td>
					<td><fmt:formatDate pattern='MM/dd/yyyy' value='${book.lastUpdateTime}'/></td>
					<td><a href="edit_book?id=${book.bookId}">Edit</a> &nbsp; <a
						href="javascript:void(0);" class="deleteLink" id="${book.bookId}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>

<script type="text/javascript">

	$(document).ready(function () {
		$(".deleteLink").each(function() {
			$(this).on("click", function() {
				bookId = $(this).attr("id");
				if(confirm('Are you sure to delete the book with Id ' + bookId + '?')) {
					window.location = 'delete_book?id=' + bookId;
					}
				});
			});
	});
	
</script>
</html>