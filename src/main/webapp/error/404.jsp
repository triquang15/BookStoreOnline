<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Page Not Found Error</title>
</head>
<body>
	<div align="center">
		<div>
			<img alt="logo" src="${pageContext.request.contextPath}/images/BookstoreLogo.png" />
		</div>


		<div>
			<h2>The webpage cannot be found</h2>
		</div>

		<div>
			<a href="javascript:history.go(-1)">Go Back </a>
		</div>
	</div>
</body>
</html>