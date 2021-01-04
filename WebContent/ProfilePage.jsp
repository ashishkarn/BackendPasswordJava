<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Profile Page</title>
	</head>
	<body>
		<p><b><%=(String)request.getAttribute("username")+"'s Profile page" %></b></p>
		<br>
		<p>Email:<%=(String)request.getAttribute("email") %></p>
		<p>Registration Date: <%=request.getAttribute("regDate") %></p>
		<p>Last Online: <%=request.getAttribute("lastOnline") %></p>
		<br>
		<form action="/EcjPractical/changepass" method="post">
			<p>New Password:</p>
			<input type="password" name="password">
			<br>
			<p><%=request.getAttribute("message") %></p>
			<input type="submit" value="submit">
		</form>
		<br>
		<form action ="/EcjPractical/logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</body>
</html>