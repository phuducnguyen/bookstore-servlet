<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<!-- TODO: The Logo image will not be loaded correctly -->
	<jsp:directive.include file="header.jsp" />
	
	<div class="center">
		<h2>PLEASE LOGIN</h2>
		<form>
			Email: <input type="text" size="10" />
			<br/>
			Password: <input type="password" size="10" />
			<input type="submit" value="Login" /> 
		</form>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
</body>
</html>