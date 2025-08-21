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
    <h2>Add New Book</h2>
    <%
        String message = request.getParameter("message");
        if(message != null) {
    %>
    <p class="message"><%= message %></p>
    <% } %>
    <form action="<%= request.getContextPath() %>/AddItemServlet" method="post">
        <label for="code">Item Code:</label>
        <input type="text" id="code" name="code" required>

        <label for="title">Book Title:</label>
        <input type="text" id="title" name="title" required>
        
        <label for="description">Short Description:</label>
        <input type="text" id="description" name="description">
        
        <label for="category">Category:</label>
        <input type="text" id="category" name="category">

        <label for="price">Item Price:</label>
        <input type="text" id="price" name="price" required>
        
        <label for="stock">Stock:</label>
        <input type="text" id="stock" name="stock" required>

        <input type="submit" value="Add">
    </form>
</div>
</body>
</html>
