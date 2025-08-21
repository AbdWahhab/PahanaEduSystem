<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Registration</title>
        <link rel="stylesheet" href="css/form.css">
    </head>
    <body>
        <div class="register-container">
            <h2>Register</h2>
            <%
                String message = request.getParameter("message");
                if(message != null) {
            %>
            <p class="message"><%= message %></p>
            <% } %>
            <form action="RegisterServlet" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>

                <label for="role">Role:</label>
                <select id="role" name="role" required>
                    <option value="customer">Customer</option>
                    <option value="admin">Admin</option>
                </select>

                <input type="submit" value="Register">
            </form>
        </div>
    </body>
</html>
