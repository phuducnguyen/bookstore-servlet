<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="../css/style.css" >
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">
			<c:if test="${category != null}">
				Edit Category
			</c:if>
			<c:if test="${category == null}">
				Create New Category
			</c:if>
		</h2>
	</div>
	
	<div align="center">
		<!-- THE EDIT MODE ACTION-->
		<c:if test="${category != null}">
			<form action="update_category" method="post" id="categoryForm">
			
			<!-- Get Category ID for POST API -->
			<input type="hidden" name="categoryId" value="${category.categoryId}">
		</c:if>
		<!-- THE CREATE MODE ACTION -->
		<c:if test="${category == null}">
			<form action="create_category" method="post" id="categoryForm">
		</c:if>
		
		<table class="form">
			<tr>
				<td align="right">Name:</td>
				<td align="left"><input type="text" id="name" name="name" size="20" value="${category.name}" /></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">Save</button>
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
	// Validate Form's Input
	$("#categoryForm").validate({
		rules: {
			name: "required",
		},
		
		messages: {
			name: "Please enter category name",
		}
	});
	
	// Handle click event of Cancel button in the form
	$("#buttonCancel").click(function() {
		history.back();
	});
});
</script>
</html>