<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category Form</title>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>
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
			<form action="update_category" method="post" onsubmit="return validateFormInput()">
			
			<!-- Get Category ID for POST API -->
			<input type="hidden" name="categoryId" value="${category.categoryId}">
		</c:if>
		<!-- THE CREATE MODE ACTION -->
		<c:if test="${category == null}">
			<form action="create_category" method="post" onsubmit="return validateFormInput()">
		</c:if>
		
		<table>
			<tr>
				<td align="right">Name:</td>
				<td align="left"><input type="text" id="name" name="name" size="20" value="${category.name}" /></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="Save">
					<input type="button" value="Cancel" onclick="javascript:history.back();"> 
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
	
</body>
<script type="text/javascript">
	function validateFormInput() {
		var fieldName = document.getElementById("name");
		
		if (fieldName.value.length == 0) {
			alert("Name is required!");
			fieldName.focus();
			return false;
		}
		
		return true;
	}
</script>
</html>