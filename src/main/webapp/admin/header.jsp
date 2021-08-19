 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <div align="center">

	<div>
		<img alt="logo" src="../images/BookstoreAdminLogo.png" />
	</div>

	<div>
		Welcome, <c:out value="${sessionScope.useremail}"></c:out> | <a href="logout">Logout</a> <br> <br>
	</div>
	<div id="header_menu" >
		<div>
			<a href="list_users"> <img alt="user" src="../images/users.png">
				<br> Users
			</a>
		</div>

		<div>
			<a href="list_category"> <img alt="Categories"
				src="../images/category.png"> <br> Categories
			</a>
		</div>

		<div>
			<a href="books"> <img alt="Books" src="../images/bookstack.png">
				<br> Books
			</a>
		</div>

		<div>
			<a href="customers"> <img alt="Customers"
				src="../images/customer.png"> <br> Customers
			</a>
		</div>


		<div>
			<a href="reviews"> <img alt="reviews" src="../images/review.png">
				<br> Reviews
			</a>
		</div >
		<div>
			<a href="orders"> <img alt="orders" src="../images/order.png">
				<br> Orders
			</a>
		</div>
	</div>
</div>