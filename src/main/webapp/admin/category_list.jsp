<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage Categories - Evergreen BookStore Administration</title>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>Category Management</h2>
		<h3>
			<a href="category_form.jsp">Create New Category</a>
		</h3>
	</div>

	<c:if test="${message != null}">
		<div align="center">
			<h4>${message}</h4>
		</div>
	</c:if>

	<div align="center">
		<table cellpadding="5" border="1">

			<tr>
				<th>Index</th>
				<th>Id</th>
				<th>Name</th>
				<th>Action</th>
			</tr>
			<c:forEach var="cat" items="${listCategory}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${cat.categoryId}</td>
					<td>${cat.name}</td>
				
					<td><a href="edit_category?id=${cat.categoryId}">Edit</a> &nbsp; <a
						href="javascript:confirmDelete(${cat.categoryId})">Delete</a></td>
				</tr>
			</c:forEach>

		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>

<script >
	function confirmDelete(categoryId) {
		if(confirm('Are you sure you want to delete the category with Id ' + categoryId + '?')){
				window.location = 'delete_category?id=' +categoryId;
			}
	}
</script>
</html>