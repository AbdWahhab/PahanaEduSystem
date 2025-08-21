<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="models.User" %>
<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp?error=notloggedin");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Registration</title>
    <link rel="stylesheet" href="../css/form.css">
</head>
<body>
<div class="register-container">
    <h2>Add New Account</h2>
    <%
        String message = request.getParameter("message");
        if(message != null) {
    %>
    <p class="message"><%= message %></p>
    <% } %>
    <form action="<%= request.getContextPath() %>/AddCustomerServlet" method="post">
        <label for="account_number">Account Number:</label>
        <input type="text" id="account_number" name="account_number" required>

        <label for="name">Customer Name:</label>
        <input type="text" id="name" name="name" required>
        
        <label for="address">Address:</label>
        <input type="text" id="address" name="address">

        <label for="phone">Mobile Number:</label>
        <input type="phone" id="phone" name="phone" required>

        <input type="submit" value="Register">
    </form>
</div>
</body>
</html>
