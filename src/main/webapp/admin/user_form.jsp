<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create New User</title>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>
			<c:if test="${user != null}">
			Edit User
			</c:if>
			<c:if test="${user == null }">
		Create New User
		</c:if>
		</h2>
	</div>

	<div align="center">
<c:if test="${user != null}">
		<form action="update_user" method="post" onsubmit="return validateFormInput()">
		<input type="hidden" name="userId" value="${user.userId}"/>
			</c:if>
			
			<c:if test="${user == null }">
			<form action="create_user" method="post" onsubmit="return validateFormInput()">
			</c:if>
			
			<table>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" id="email" size="20" value="${user.email}"/></td>
				</tr>
				<tr>
					<td>Full Name:</td>
					<td><input type="text" name="fullname" id="fullname" size="20" value="${user.fullName}"/></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" id="password" size="20" value="${user.password}"/></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
				</tr>

				<tr>

					<td colspan="2" align="center">
					<input type="submit"
						value="Save"> 
					<input type="button" value="Cancel" onclick="javascript:history.go(-1);"></td>
				</tr>

			</table>
		</form>
	</div>


	<jsp:directive.include file="footer.jsp" />
</body>

<script type="text/javascript">
	function validateFormInput() {
		var fieldEmail = document.getElementById("email");
		var fieldFullname = document.getElementById("fullname");
		var fieldPassword = document.getElementById("password");

		if (fieldEmail.value.length == 0) {
			alert("Email is Required!");
			fieldEmail.focus();
			return false;
		}

		if (fieldFullname.value.length == 0) {
			alert("Fullname is Required!");
			fieldFullname.focus();
			return false;
		}

		if (fieldPassword.value.length == 0) {
			alert("Password is Required!");
			fieldPassword.focus();
			return false;
		}

		return true;
	}
</script>
</html>