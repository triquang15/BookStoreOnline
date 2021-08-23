
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">

	<div>
		<img alt="logo" src="images/BookstoreLogo.png">

	</div>

	<div>
		<form action="search" method="get">
			<input type="text" name="keyword" size="50" /> <input type="submit"
				value="Search" />
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="login">Sign In</a>
		| <a href="register">Register</a> | <a href="view_card">Cart</a>
		</form>
	</div>
	<br>
	<div>
		<c:forEach var="category" items="${listCategories}" varStatus="status">
			<a href="view_category?id=${category.categoryId}"> <font
				size="+1"> <b><c:out value="${category.name}" /> </b></font>
			</a>
			<c:if test="${not status.last}">
		&nbsp; | &nbsp;
		</c:if>
		</c:forEach>
	</div>
</div>