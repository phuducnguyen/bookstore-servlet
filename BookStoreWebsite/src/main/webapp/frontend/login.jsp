<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Customer Login</title>
	<link rel="stylesheet" href="css/style.css" >
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2>Customer Login</h2>
		
		<c:if test="${message != null}">
			<div align="center">
				<h4 class="message">${message}</h4>
			</div>
		</c:if>
		<form id="loginForm" action="login" method="post">
			<table>
				<tr>
					<td>Email:</td> 
					<td><input type="text" name="email" id="email" size="20"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" id="password" size="20"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit">Login</button>
				</tr>
			</table>
		</form>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
	
<script type="text/javascript">
	$(document).ready(function() {
		// Validate Login Form's Input
		$("#loginForm").validate({
			rules : {
				email : {
					required : true,
					email : true
				},
	
				password : "required",
			},
	
			messages : {
				email : {
					required: "Please enter email",
					email: "Please enter an valid email address"
				},
				
				password : "Please enter password"
			}
		});
	});
</script>
</body>
</html>