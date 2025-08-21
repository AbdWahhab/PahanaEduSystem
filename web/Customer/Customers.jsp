<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="models.User, DAO.CustomerDAO, models.Customer, java.util.List" %>
<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp?error=notloggedin");
        return;
    }
    
    String keyword = request.getParameter("search");
    List<Customer> customers;
    if (keyword != null && !keyword.trim().isEmpty()) {
        customers = CustomerDAO.searchCustomers(keyword);
    } else {
        customers = CustomerDAO.getAllCustomers();
    }
%>

<div id="contentArea" data-source="Customer/Customers.jsp">

    <h2>Available Customers</h2>

    <div class="actions">
        <div class="left-actions">
            <div class="search-bar">
                <input type="text" name="search" placeholder="Search customers..." value="<%= (keyword != null ? keyword : "")%>">
            </div>
        </div>
        <div class="right-actions">
            <a href="Customer/addCustomer.jsp">Add Customer</a>
        </div>
    </div>

    <div class="table-container" id="tableContainer">
        <table>
            <tr>
                <th>Account Number</th>
                <th>Name</th>
                <th>Address</th>
                <th>Phone</th>
                <th>Action</th>
            </tr>
            <%
                if (customers != null && !customers.isEmpty()) {
                    for (Customer c : customers) {
            %>
            <tr>
                <td><%= c.getAccountNumber()%></td>
                <td><%= c.getName()%></td>
                <td><%= c.getAddress()%></td>
                <td><%= c.getPhone()%></td>
                <td>
                    <a class="edit-btn" href="Customer/editCustomer.jsp?accountNumber=<%= c.getAccountNumber()%>">Update</a>
                </td>
            </tr>
            <%      }
        } else { %>
            <tr>
                <td colspan="5">No customers found!</td>
            </tr>
            <% }%>
        </table>
    </div>

</div>
