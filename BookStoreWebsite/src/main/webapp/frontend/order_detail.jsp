<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Order Details | Evergreen Green Bookstore</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">Your Order ID: ${order.orderId}</h2>
	</div>

	<div align="center">
		<table>
			<tr>
				<td>Order Status:</td>
				<td>${order.status}</td>
			</tr>
			<tr>
				<td>Order Date:</td>
				<td>${order.orderDate}</td>
			</tr>
			<tr>
				<td>Quantity:</td>
				<td>${order.bookCopies}</td>
			</tr>
			<tr>
				<td>Total Amount:</td>
				<td><fmt:formatNumber value="${order.total}" type="currency"
						currencySymbol="$" /></td>
			</tr>
			<tr>
				<td>Recipient Name:</td>
				<td>${order.recipientName}</td>
			</tr>
			<tr>
				<td>Recipient Phone:</td>
				<td>${order.recipientPhone}</td>
			</tr>
			<tr>
				<td>Ship To:</td>
				<td>${order.shippingAddress}</td>
			</tr>
			<tr>
				<td>Payment Method:</td>
				<td>${order.paymentMethod}</td>
			</tr>
		</table>
	</div>

	<div align="center">
		<h2>Ordered Books</h2>
		<table border="1">
			<tr>
				<td>Index</td>
				<td>Book</td>
				<td>Author</td>
				<td>Price</td>
				<td>Quantity</td>
				<td>Subtotal</td>
			</tr>

			<c:forEach items="${order.orderDetails}" var="orderDetail"
				varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td><img style="vertical-align: middle;"
						src="data:image/jpg;base64,${orderDetail.book.base64Image}"
						width="45" height="60" /> 
						${orderDetail.book.title}</td>
					<td>${orderDetail.book.author}</td>
					<td><fmt:formatNumber value="${orderDetail.book.price}"
							type="currency" currencySymbol="$" /></td>
					<td>${orderDetail.quantity}</td>
					<td><fmt:formatNumber value="${orderDetail.subtotal}"
							type="currency" currencySymbol="$" /></td>
				</tr>
			</c:forEach>

			<tr>
				<td colspan="4" align="right"><b><i>TOTAL:</i></b></td>
				<td><b>${order.bookCopies}</b></td>
				<td><b><fmt:formatNumber value="${order.total}"
							type="currency" currencySymbol="$" /></b></td>
			</tr>
		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>
