<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Customer Profile | Online Books Store</title>	
	
	<link rel="stylesheet" href="css/style.css" >	
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>	
</head>
<body>
<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">
			Edit My Profile
		</h2>
	</div>
	
	<div align="center">
		<form action="update_profile" method="post" id="customerForm">
		
		<table class="form">
			<tr>
				<td align="right">E-mail:</td>
				<td align="left"><b>${loggedCustomer.email}</b></td>
			</tr>	
			<tr>
				<td align="right">Full Name:</td>
				<td align="left"><input type="text" id="fullName" name="fullName" size="42" value="${loggedCustomer.fullname}" /></td>
			</tr>
			<tr>
				<td align="right">Phone Number:</td>
				<td align="left"><input type="text" id="phone" name="phone" size="42" value="${loggedCustomer.phone}" /></td>
			</tr>
			<tr>
				<td align="right">Address:</td>
				<td align="left"><input type="text" id="address" name="address" size="42" value="${loggedCustomer.address}" /></td>
			</tr>
			<tr>
				<td align="right">City:</td>
				<td align="left"><input type="text" id="city" name="city" size="42" value="${loggedCustomer.city}" /></td>
			</tr>
			<tr>
				<td align="right">Zip Code:</td>
				<td align="left"><input type="text" id="zipCode" name="zipCode" size="42" value="${loggedCustomer.zipcode}" /></td>
			</tr>
			<tr>
				<td align="right">Country:</td>
				<td align="left"><input type="text" id="country" name="country" size="42" value="${loggedCustomer.country}" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<i>(Leave password fields blank if you don't want to change password)</i>
				</td>
			</tr>
			<tr>
				<td align="right">Password:</td>
				<td align="left"><input type="password" id="password" name="password" size="42" /></td>
			</tr>
			<tr>
				<td align="right">Confirm Password:</td>
				<td align="left"><input type="password" id="confirmPassword" name="confirmPassword" size="42" /></td>
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
		// Validate Form's Input
		$("#customerForm").validate({
			rules: {
				email: {
					required: true,
					email: true
				},
				fullName: "required",
				
				confirmPassword: {
					equalTo: "#password"
				},
				
				phone: "required",
				address: "required",
				city: "required",
				zipCode: "required",
				country: "required",
			},
			
			messages: {
				email: {
					required: "Please enter e-mail address",
					email: "Please enter a valid e-mail address"
				},
				
				fullName: "Please enter full name",
				
				confirmPassword: {
					equalTo: "Confirm password does not match password"
				},
				
				phone: "Please enter phone number",
				address: "Please enter address",
				city: "Please enter city",
				zipCode: "Please enter zip code",
				country: "Plese enter country",
			}
		});
		
		// Handle click event of Cancel button in the form
		$("#buttonCancel").click(function() {
			history.back();
		});
	});
</script>
</html>