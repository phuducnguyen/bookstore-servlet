<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Profile - Online Books Store</title>

<link rel="stylesheet" href="css/style.css" >
</head>
<body>

	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<br/>
		<h2>Welcome, ${loggedCustomer.fullname}</h2>
		<br/>
	
		<table class="normal">
			<tr>
				<td>E-mail Address:</td>
				<td>${loggedCustomer.email}</td>
			</tr>
			<tr>
				<td>Full Name:</td>
				<td>${loggedCustomer.fullname}</td>
			</tr>
			<tr>
				<td>Phone Number:</td>
				<td>${loggedCustomer.phone}</td>
			</tr>
			<tr>
				<td>Address:</td>
				<td>${loggedCustomer.address}</td>
			</tr>
			<tr>
				<td>City:</td>
				<td>${loggedCustomer.city}</td>
			</tr>
			<tr>
				<td>Zip Code:</td>
				<td>${loggedCustomer.zipcode}</td>
			</tr>
			<tr>
				<td>Country:</td>
				<td>${loggedCustomer.country}</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center"><a href="edit_profile">Edit My Profile</a></td>
			</tr>
		</table>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
	
</body>
</html>