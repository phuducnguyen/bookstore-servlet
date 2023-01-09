<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Your Shopping Cart</title>
	
	<link rel="stylesheet" href="css/style.css" >
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2>Your Shopping Cart Details</h2>

		<c:if test="${message != null}">
			<div align="center">
				<h4 class="message">${message}</h4>
			</div>
		</c:if>

		<c:set var="cart" value="${sessionScope['cart']}" />

		<c:if test="${cart.totalItems == 0}">
			<h2>There's no items in your cart</h2>
		</c:if>

		<c:if test="${cart.totalItems > 0}">
			<div>
				<form>
					<table border="1">
						<tr>
							<th>No</th>
							<th colspan="2">Book</th>
							<th>Quantity</th>
							<th>Price</th>
							<th>Subtotal</th>
							<th>
								<a href=""><b>Clear Cart</b></a>
							</th>
						</tr>
						
						<c:forEach items="${cart.items}" var="item" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>
								<img class="book-small" src="data:image/jpg;base64,${item.key.base64Image}" />		
							</td>					
							<td>
								&nbsp;&nbsp;
								<span id="book-title">${item.key.title}</span>
							</td>
							<td>${item.value}</td>
							<td><fmt:formatNumber value="${item.key.price}" type="currency" currencySymbol="$"/></td>
							<td><fmt:formatNumber value="${item.value * item.key.price}" type="currency" currencySymbol="$"/></td>
							<td><a href="">Remove</a></td>
						</tr>		
						</c:forEach>
						
						<tr>
							<td></td>
							<td></td>
							<td><b>${cart.totalQuantity} book(s)</b></td>
							<td>Total:</td>
							<td colspan="2"><b><fmt:formatNumber value="${cart.totalAmount}" type="currency" currencySymbol="$"/></b></td>
						</tr>
					</table>
				</form>
			</div>
		</c:if>

	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>