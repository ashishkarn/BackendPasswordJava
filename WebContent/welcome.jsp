<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title></title>
</head>

<body>
    <p><b><%=request.getAttribute("message")%></b></p>
    <ul>
        <li>
            <form action="/EcjPractical/loginpage" method="post"><input type="submit" value="Login"></form>
        </li>
        <li>
            <form action="/EcjPractical/signuppage" method="post"><input type="submit" value="SignUp"></form>
        </li>
    </ul>
</body>

</html>