<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign Up!</title>
</head>
<body>
<p>SignUp</p>
<form action="/EcjPractical/signup" method="post">
<p>Name:</p>
<input type="text" name="name">
<br>
<p>Username:</p>
<input type="text" name="username">
<br>
<p>Password:</p>
<input type="password" name="password">
<br>
<p>Email:</p>
<input type="text" name="email">
<br>
<p><%=request.getAttribute("message") %></p>
<input type="submit" value="submit">
</form>
</body>
</html>