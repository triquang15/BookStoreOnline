<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create New Book</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/jquery-ui.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="..//css/richtext.min.css">	

<script type="text/javascript" src="../js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js/jquery.richtext.min.js"></script>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="page_heading">
			<c:if test="${book != null}">
			Edit Book
			</c:if>
			<c:if test="${book == null }">
		Create New Book
		</c:if>
		</h2>
	</div>

	<div align="center">
		<c:if test="${book != null}">
			<form action="update_book" method="post" id="bookForm" enctype="multipart/form-data">
				<input type="hidden" name="bookId" value="${book.bookId}" />
		</c:if>

		<c:if test="${book == null }">
			<form action="create_book" method="post" id="bookForm" enctype="multipart/form-data">
		</c:if>

		<table class="form">
			<tr>
				<td>Category:</td>
				<td><select name="category">
						<c:forEach items="${listCategories}" var="category">
						<c:if test="${category.categoryId eq book.category.categoryId}">
							<option value="${category.categoryId}" selected>
						</c:if>
						<c:if test="${category.categoryId ne book.category.categoryId}">
								<option value="${category.categoryId}">
						</c:if>
							${category.name}
							</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Title:</td>
				<td><input type="text" name="title" id="title" size="20"
					value="${book.title}" /></td>
			</tr>
			<tr>
				<td>Author:</td>
				<td><input type="text" name="author" id="author" size="20"
					value="${book.author}" /></td>
			</tr>

			<tr>
				<td>ISBN:</td>
				<td><input type="text" name="isbn" id="isbn" size="20"
					value="${book.isbn}" /></td>
			</tr>

			<tr>
				<td>Publish Date:</td>
				<td><input type="text" name="publishDate" id="publishDate"
					size="20" value="<fmt:formatDate pattern='MM/dd/yyyy' value='${book.publishDate}'/>" /></td>
			</tr>

			<tr>
				<td>Book Image:</td>
				<td><input type="file" name="bookImage" id="bookImage" 
					size="20" /> <br> 
					<img id="thumbnail" alt="image" style="width:20%; margin-top: 10px"
					src="data:image/jpg;base64,${book.base64Image}" />
					</td>
			</tr>

			<tr>
				<td>Price:</td>
				<td><input type="text" name="price" id="price" size="20"
					value="${book.price}" /></td>
			</tr>

			<tr>
				<td>Description:</td>
				<td><textarea rows="5" cols="50" name="description"
						id="description">${book.description}</textarea></td>
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
		$("#publishDate").datepicker();
		$('#description').richText();
		$("#bookImage").change(function() {
			showImage(this);
		});

		
		$("#bookForm").validate({
			rules : {
				category: "required",
				title: "required",
				author : "required",
				isbn : "required",
				publishDate : "required",

				<c:if test="${book == null}">
				bookImage : "required",
				</c:if>
				price : "required",
				description : "required",
			},

			messages : {
				
				title : "Please select a category for the book ",
				title : "Please enter book title",
				author : "Please enter book author",
				isbn : "Please enter book isbn",
				publishDate : "Please enter publish Date",
				bookImage : "Please choose an image of the book",
				price : "Please enter book price",
				description : "Please enter book description",
			}
		});

		$("#buttonCancel").click(function() {
			history.go(-1);
		});
	});

	function showImage(fileInput) {
		var file = fileInput.files[0];

		var reader = new FileReader();

		reader.onload = function(e) {
			$('#thumbnail').attr('src', e.target.result);
		};

		reader.readAsDataURL(file);
	}
</script>
</html>