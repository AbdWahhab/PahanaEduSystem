<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="css/form.css">
</head>
<body>
<div class="login-container">
    <h2>Login</h2>

    <%
        String error = request.getParameter("error");
        String message = request.getParameter("message");

        if ("invalid".equals(error)) {
    %>
    <p class="error">Invalid username or password!</p>
    <%
    } else if ("db".equals(error)) {
    %>
    <p class="error">Database connection error!</p>
    <%
    } else if ("exception".equals(error)) {
    %>
    <p class="error">Unexpected error occurred!</p>
    <%
    } else if ("notloggedin".equals(error)) {
    %>
    <p class="error">Please login first.</p>
    <%
        }

        if (message != null) {
    %>
    <p class="message"><%= message %></p>
    <%
        }
    %>

    <form action="loginServlet" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Login">
    </form>
</div>
</body>
</html>
