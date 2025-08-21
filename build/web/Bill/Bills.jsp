<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="models.User, DAO.BillDAO, models.Bill, models.Customer, models.Item, DAO.CustomerDAO, DAO.ItemDAO, java.util.List, java.text.SimpleDateFormat" %>
<%
    List<Bill> bills = BillDAO.getAllBills();
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp?error=notloggedin");
        return;
    }

    List<Customer> customers = CustomerDAO.getAllCustomers();
    List<Item> items = ItemDAO.getAllItems();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // format date as day/month/year
%>

<div id="contentArea" data-source="Customer/Customers.jsp">

    <h2>Sold Books</h2>

    <div class="actions">
        <div class="right-actions">
            <a href="Bill/createBill.jsp">Add Bill</a>
        </div>
    </div>

    <div class="table-container" id="tableContainer">
        <table>
            <tr>
                <th>Account Number</th>
                <th>Customer</th>
                <th>Item Code</th>
                <th>Title</th>
                <th>Units</th>
                <th>Unit Price</th>
                <th>Total</th>
                <th>Date</th>
            </tr>
            <%
                if (bills != null && !bills.isEmpty()) {
                    for (Bill c : bills) {
                        Customer cust = null;
                        for (Customer x : customers) {
                            if (x.getId() == c.getCustomerId()) {
                                cust = x;
                                break;
                            }
                        }
                        Item itm = null;
                        for (Item y : items) {
                            if (y.getId() == c.getItemId()) {
                                itm = y;
                                break;
                            }
                        }

                        if (cust != null && itm != null) {
            %>
            <tr>
                <td><%= cust.getAccountNumber()%></td>
                <td><%= cust.getName()%></td>
                <td><%= itm.getCode()%></td>
                <td><%= itm.getTitle()%></td>
                <td><%= c.getUnits()%></td>
                <td><%= c.getUnitPrice()%></td>
                <td><%= c.getTotalAmount()%></td>
                <td><%= sdf.format(c.getBillingDate())%></td>
            </tr>
            <%
                    }
                }
            } else {
            %>
            <tr>
                <td colspan="8">No customers found!</td>
            </tr>
            <%
                }
            %>
        </table>
    </div>

</div>
