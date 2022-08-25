<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Book Form</title>	
	<link rel="stylesheet" href="../css/jquery-ui.min.css" >
	<link rel="stylesheet" href="../css/style.css" >
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
</head>
<body>
<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">
			<c:if test="${book != null}">
				Edit Book
			</c:if>
			<c:if test="${book == null}">
				Create New Book
			</c:if>
		</h2>
	</div>
	
	<div align="center">
		<!-- THE EDIT MODE ACTION-->
		<c:if test="${book != null}">
			<form action="update_book" method="post" id="bookForm">
			<input type="hidden" name="bookId" value="${book.bookId}">
		</c:if>
		<!-- THE CREATE MODE ACTION -->
		<c:if test="${book == null}">
			<form action="create_book" method="post" id="bookForm">
		</c:if>
		
		<table class="form">
			<tr>
				<td>Category:</td>
				<td>
				<!-- The Combobox list all categories in Alphabet Order -->
					<select name="category">
						<c:forEach items="${listCategories}" var="category">
							<option value="${category.categoryId}">
								${category.name}
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		
			<tr>
				<td align="right">Title:</td>
				<td align="left"><input type="text" id="title" name="title" size="20" value="${book.title}" /></td>
			</tr>
			
			<tr>
				<td align="right">Author:</td>
				<td align="left"><input type="text" id="author" name="author" size="20" value="${book.author}" /></td>
			</tr>
			
			<tr>
				<td align="right">ISBN:</td>
				<td align="left"><input type="text" id="isbn" name="isbn" size="20" value="${book.isbn}" /></td>
			</tr>
			
			<tr>
				<td align="right">Publish Date:</td>
				<td align="left"><input type="text" id="publishDate" name="publishDate" size="20" value="${book.publishDate}" /></td>
			</tr>
			
			<tr>
				<td align="right">Book Image:</td>
				<td align="left">
					<input type="file" id="bookImage" name="bookImage" size="20" /><br/>
					<img id="thumbnail" alt="Image Preview" style="width:20%; margin-top: 10px;" />
				</td>
			</tr>
			
			<tr>
				<td align="right">Price:</td>
				<td align="left"><input type="text" id="price" name="price" size="20" value="${book.price}" /></td>
			</tr>
			
			<tr>
				<td align="right">Description:</td>
				<td align="left">
					<textarea rows="5" cols="50" name="description" id="description"></textarea>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
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
	// JQuery specify
	$(document).ready(function() {
		// Select a date from a popup or inline calendar
	    $( "#publishDate" ).datepicker({
	    	inline: true
	    });
		
		// Change event for bookImage
		$('#bookImage').change(function() {
			showImageThumbnail(this);
		});
		
		// Validate Form's Input
		$("#bookForm").validate({
			rules: {
				category: "required",
				title: "required",
				author: "required",
				isbn: "required",
				publishDate: "required",
				bookImage: "required",
				price: "required",
				description: "required",
			},
			
			messages: {
				category: "Please select a category for the book",
				title: "Please enter book title",
				author: "Please enter book author",
				isbn: "Please enter ISBN of the book",
				publishDate: "Please enter publish date of the book, format type [mm/dd/yyyy]",
				bookImage: "Please choose an image of the book",
				price: "Please enter price of the book",
				description: "Please enter description of the book",
			}
		});
		
		// Handle click event of Cancel button in the form
		$("#buttonCancel").click(function() {
			history.back();
		});
		
		function showImageThumbnail(fileInput) {
			var file = fileInput.files[0];
			
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#thumbnail').attr('src', e.target.result);
			};
			
			reader.readAsDataURL(file);
		}
	});
</script>
</html>