<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login!</title>
</head>
<body>
<p>Login</p>
<form action="/EcjPractical/login" method="post">
<p>Username:</p>
<input type="text" name="username">
<br>
<p>Password:</p>
<input type="password" name="password">
<br>
<p><%=request.getAttribute("message") %></p>
<input type="submit" value="submit">
</form>
<form action ="/EcjPractical/signuppage" method="post">
			<input type="submit" value="SignUp">
</form>
<form action ="/EcjPractical/forgotpasspage" method="post">
			<input type="submit" value="Forgot Password?">
</form>
</body>
</html>