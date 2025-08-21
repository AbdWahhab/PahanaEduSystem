<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="DAO.ItemDAO, models.Item" %>
<%
    String code = request.getParameter("code");
    Item item = ItemDAO.filterItemById(code);

    if (item == null) {
        response.sendRedirect("Item.jsp?message=Item+not+found");
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
        <title>Edit Item</title>
        <link rel="stylesheet" href="../css/form.css">
    </head>
    <body>
        <div class="edit-container">
            <h2>Update Book Details</h2>
            <%
                String message = request.getParameter("message");
                if (message != null) {
            %>
            <p class="message"><%= message%></p>
            <% }%>
            <form action="<%= request.getContextPath()%>/UpdateItemServlet" method="post">

                <label for="code">Item Code:</label>
                <input type="text" id="code" name="code" value="<%= item.getCode()%>" readonly>

                <label for="title">Book Title:</label>
                <input type="text" id="title" name="title" value="<%= item.getTitle()%>" required>

                <label for="description">Short Description:</label>
                <input type="text" id="description" name="description" value="<%= item.getDescription()%>">

                <label for="category">Category:</label>
                <input type="text" id="category" name="category" value="<%= item.getCategory()%>" required>

                <label for="price">Price:</label>
                <input type="text" id="price" name="price" value="<%= item.getPrice()%>" required>

                <label for="stock">Stock:</label>
                <input type="text" id="stock" name="stock" value="<%= item.getStock()%>" required>

                <input type="submit" value="Update">
            </form>
        </div>
    </body>
</html>
