<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Review</title>
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src="../js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="page_heading">Edit Review</h2>
	</div>

	<div align="center">

		<form action="update_review" method="post" id="reviewForm">
			<input type="hidden" name="reviewId" value="${review.reviewId}" />

			<table class="form">

				<tr>
					<td align="center">Book:</td>
					<td align="left"><b>${review.book.title}</b></td>
				</tr>
				<tr>
					<td align="center">Rating:</td>
					<td align="left"><b>${review.rating}</b></td>
				</tr>
				
				<tr>
					<td align="center">Customer:</td>
					<td align="left"><b>${review.customer.fullname}</b></td>
				</tr>
				
				<tr>
					<td align="center">Headline:</td>
					<td align="left">
						<input type="text" size="60" name="headline" value="${review.headline}"/>
					</td>
				</tr>
				
				<tr>
					<td align="center">Comment:</td>
					<td align="left">
						<textarea rows="5" cols="70" name="comment">${review.comment}</textarea>
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
		$("#reviewForm").validate({
			rules : {

				headline : "required",
				comment : "required"
			},

			messages : {

				headline : "Please enter headline ",
				comment : "Please enter comment ",
			}
		});
		$("#buttonCancel").click(function() {
			history.go(-1);
		});
	});
</script>
</html>