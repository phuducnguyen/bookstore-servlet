<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center">
	<div>
		<img src="images/BookstoreLogo.png" />
	</div>
	
	<div>
		<form action="search" method="get">
			<input type="text" name="keyword" size="50" />
			<input type="submit" value="Search" />
		
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="Login">Sign In</a> |
			<a href="register">Register</a> |
			<a href="view_cart">Cart</a>
		</form>
	</div>
	<div>&nbsp;</div>
	<div>
		<c:forEach var="category" items="${listCategories}" varStatus="status">
			<a href="view_category?id=${category.categoryId}">
				<!-- BUG: After updated, the category do not show correctly in HomePage-->
				<!-- The <font> tag is deprecated, REPLACE IT later -->
				<font size="+1"><b><c:out value="${category.name}"></c:out></b></font>
			</a>
			<c:if test="${not status.last}">
			&nbsp; | &nbsp;
			</c:if>
		</c:forEach>
	</div>
</div>