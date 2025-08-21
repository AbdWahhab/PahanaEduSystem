<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="DAO.CustomerDAO, models.Customer" %>
<%
    String accountNumber = request.getParameter("accountNumber");
    Customer customer = CustomerDAO.filterCustomerById(accountNumber);

    if (customer == null) {
        response.sendRedirect("Customers.jsp?message=Customer+not+found");
        return;
    }
%>
<%@page import="models.User"%>
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
        <title>Edit Customer</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/form.css">
    </head>
    <body>
        <div class="edit-container">
            <h2>Update Customer</h2>
            <%
                String message = request.getParameter("message");
                if (message != null) {
            %>
            <p class="message"><%= message%></p>
            <% }%>
            <form action="<%= request.getContextPath()%>/UpdateCustomerServlet" method="post">
                <label for="account_number">Account Number:</label>
                <input type="text" id="account_number" name="account_number" value="<%= customer.getAccountNumber()%>" readonly>

                <label for="name">Customer Name:</label>
                <input type="text" id="name" name="name" value="<%= customer.getName()%>" required>

                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="<%= customer.getAddress()%>">

                <label for="phone">Mobile Number:</label>
                <input type="phone" id="phone" name="phone" value="<%= customer.getPhone()%>" required>

                <input type="submit" value="Update">
            </form>
        </div>
    </body>
</html>
