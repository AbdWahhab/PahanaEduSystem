<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.User, DAO.ItemDAO, models.Item, java.util.List" %>
<% User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp?error=notloggedin");
        return;
    }
    String keyword = request.getParameter("search");
    List<Item> items;
    if (keyword != null && !keyword.trim().isEmpty()) {
        items = ItemDAO.searchItems(keyword);
    } else {
        items = ItemDAO.getAllItems();
    } %>
<div id="contentArea" data-source="Item/Item.jsp"><h2>Available Books</h2>
    <div class="actions">
        <div class="left-actions">
            <div class="search-bar"><input type="text" name="search" placeholder="Search books..."
                                           value="<%= (keyword != null ? keyword : "")%>"></div>
        </div>
        <div class="right-actions"><a href="Item/addItem.jsp">Add New Book</a></div>
    </div>
    <div class="table-container" id="tableContainer">
        <table>
            <tr>
                <th>Item Code</th>
                <th>Title</th>
                <th>Description</th>
                <th>Category</th>
                <th>Price</th>
                <th>Stock</th>
                <th>Action</th>
            </tr>
            <% if (items != null && !items.isEmpty()) {
                for (Item i : items) { %>
            <tr>
                <td><%= i.getCode()%>
                </td>
                <td><%= i.getTitle()%>
                </td>
                <td><%= i.getDescription()%>
                </td>
                <td><%= i.getCategory()%>
                </td>
                <td><%= i.getPrice()%>
                </td>
                <td><%= i.getStock()%>
                </td>
                <td>
                    <a class="edit-btn" href="Item/editItem.jsp?code=<%= i.getCode()%>">Update</a>
                    <a class="delete-btn" href="<%=request.getContextPath()%>/DeleteItemServlet?code=<%= i.getCode()%>"
                       onclick="return confirm('Are you sure you want to delete this item?');">
                        Delete
                    </a>
                </td>
            </tr>
            <% }
            } else { %>
            <tr>
                <td colspan="7">No books found!</td>
            </tr>
            <% }%></table>
    </div>
</div>