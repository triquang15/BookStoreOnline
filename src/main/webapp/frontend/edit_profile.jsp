<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register as a Customer</title>
<link rel="stylesheet" href="css/style.css">

<script type="text/javascript" src="js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="page_heading">Edit My Profile</h2>
	</div>

	<div align="center">

		<form action="update_profile" method="post" id="customerForm">

			<table class="form">

				<tr>
					<td>Email:</td>
					<td><b>${loggedCustomer.email}</b> (Cannot be change)</td>
				</tr>
				<tr>
				<td>First Name:</td>
				<td><input type="text" name="firstname" id="firstname"
					size="45" value="${loggedCustomer.firstname}" /></td>
			</tr>

			<tr>
				<td>Last Name:</td>
				<td><input type="text" name="lastname" id="lastname" size="45"
					value="${loggedCustomer.lastname}" /></td>
			</tr>

				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" id="password"
						size="45" /></td>
				</tr>

				<tr>
					<td>Confirm Password :</td>
					<td><input type="password" name="confirmPassword"
						id="confirmPassword" size="45" /></td>
				</tr>

				<tr>
					<td>Phone Number:</td>
					<td><input type="text" name="phone" id="phone" size="45" value="${loggedCustomer.phone}"/></td>
				</tr>

				<tr>
				<td>Address Line 1:</td>
				<td><input type="text" name="address1" id="address1" size="45"
					value="${loggedCustomer.addressLine1}" /></td>
			</tr>

			<tr>
				<td>Address Line 2:</td>
				<td><input type="text" name="address2" id="address2" size="45"
					value="${loggedCustomer.addressLine2}" /></td>
			</tr>
			<tr>
				<td>State:</td>
				<td><input type="text" name="state" id="state" size="45"
					value="${loggedCustomer.state}" /></td>
			</tr>
				<tr>
					<td>City:</td>
					<td><input type="text" name="city" id="city" size="45" value="${loggedCustomer.city}"/></td>
				</tr>
				<tr>
					<td>Zip Code:</td>
					<td><input type="text" name="zipCode" id="zipCode" size="45"value="${loggedCustomer.zipcode}" /></td>
				</tr>
				<tr>
					<td>Country:</td>
				<td>
					<select name="country" id="country">
						<c:forEach items="${mapCountries}" var="country">
							<option value="${country.value}" <c:if test='${loggedCustomer.country eq country.value}'>selected='selected'</c:if> >${country.key}</option>
						</c:forEach>
				</select>
				</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
				</tr>

				<tr>

					<td colspan="2" align="center">
						<button type="submit">Save</button>&nbsp;&nbsp;&nbsp;

						<button id="buttonCancel">Cancel</button>
					</td>
				</tr>

			</table>
		</form>
	</div>


	<jsp:directive.include file="footer.jsp" />
</body>

<script type="text/javascript">
	$(document).ready(function() {

		$("#customerForm").validate({
			rules : {
				email : {
					required : true,
					email : true
				},
				fullName : "required",
				password : "required",

				confirmPassword : {
					required : true,
					equalTo : "#password"
				},

				phone : "required",
				address : "required",
				city : "required",
				zipCode : "required",
				country : "required",
			},

			messages : {
				email : {
					required : "Please enter email address",
					email : "Please enter a valid email address"
				},
				fullName : "Please enter full name",
				password : "Please enter password",

				confirmPassword : {
					required : "Please enter confirm password",
					equalTo : "Password does not match"
				},

				phone : "Please enter phone number",
				address : "Please enter address",
				city : "Please enter city",
				zipCode : "Please enter zipcode",
				country : "Please enter country",
			}
		});

		$("#buttonCancel").click(function() {
			history.go(-1);
		});
	});
</script>
</html>