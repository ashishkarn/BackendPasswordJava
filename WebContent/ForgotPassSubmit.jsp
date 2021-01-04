<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Forgotpass!</title>
</head>
<body>
<p>Forgot Pass</p>
<form action="/EcjPractical/forgotpass" method="post">
<p>Email:</p>
<input type="text" name="email">
<br>
<p><%=request.getAttribute("message") %></p>
<input type="submit" value="submit">
</form>
</body>
</html>