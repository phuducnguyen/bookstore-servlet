<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Write Review - Online Book Store</title>	
	
	<link rel="stylesheet" href="css/style.css" >
	<!-- RateYo! is a tiny and flexible jQuery star rating plugin, it uses SVG to render rating -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">	

	<!-- Compiled and Minified JavaScript -->
	<script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<form id="reviewForm">
			<table class="normal" width="55%">
				<tr>
					<td><h3>You already wrote a review for this book</h3></td>
					<td>&nbsp;</td>
					<td><h2>${loggedCustomer.fullname}</h2></td>
				</tr>
				<tr>
					<td colspan="3"><hr/></td>
				</tr>
				<tr>
					<td>
						<span id="book-title">${book.title}</span><br/>
						<img class="book-large" src="data:image/jpg;base64,${book.base64Image}" />
					</td>
					<td>
						<div id="rateYo"></div><br/>
						<input type="text" name="headline" size="60" readonly="readonly" value="${review.headline}" />
						<br/><br/>
						<textarea name="comment" rows="10" cols="70" readonly="readonly">${review.comment}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<jsp:directive.include file="footer.jsp" />

<script type="text/javascript">
	$(document).ready(function() {		
		$("#rateYo").rateYo({
	    	starWidth: "40px",
	    	fullStar: true,
			rating: ${review.rating},
			readOnly: true
		});
	});
</script>

</body>
</html>