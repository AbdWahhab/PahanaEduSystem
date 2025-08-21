<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="models.User, models.Customer, models.Item, models.Bill, DAO.CustomerDAO, DAO.BillDAO, DAO.ItemDAO, java.util.List, java.text.SimpleDateFormat" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp?error=notloggedin");
        return;
    }

    List<Bill> bills = BillDAO.getAllBills();
    List<Customer> customers = CustomerDAO.getAllCustomers();
    List<Item> items = ItemDAO.getAllItems();

    int totalCustomers = (customers != null) ? customers.size() : 0;

    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(new java.util.Date());

    double todaySales = 0.0;

    if (bills != null) {
        for (Bill b : bills) {
            String billDate = sdf.format(b.getBillingDate());
            if (today.equals(billDate)) {
                todaySales += b.getTotalAmount();
            }
        }
    }

    int lowStockThreshold = 5;
    java.util.List<Item> lowStockItems = new java.util.ArrayList<>();

    if (items != null) {
        for (Item item : items) {
            if (item.getStock() < lowStockThreshold) {
                lowStockItems.add(item);
            }
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Dashboard - Pahana Edu Book Shop</title>
        <link rel="stylesheet" href="css/dashboard.css">
    </head>
    <body>

        <!-- Sidebar -->
        <div class="sidebar">
           <img src="images/logo_white.png" alt="Pahana Edu Logo" style="width: 160px; margin-bottom: 20px;">
            <a href="dashboard.jsp">Dashboard</a>
            <a href="#" onclick="loadContent('Customer/Customers.jsp')">Customers</a>
            <a href="#" onclick="loadContent('Item/Item.jsp')">Books</a>
            <a href="#" onclick="loadContent('Bill/Bills.jsp')">Bills</a>
            <a href="#" onclick="loadContent('register.jsp')">Create User Account</a>
            <a href="#" onclick="loadContent('Help.jsp')">Help</a>
            <a href="LogoutServlet" onclick="return confirmLogout();">Logout</a>
        </div>

        <!-- Main Content -->
        <div class="main-content" id="mainContent">
            <header>
                <div style="display: flex; justify-content: space-between; align-items: center;">
                    <div class="welcome-msg">
                        Welcome, <strong><%= user.getUsername()%></strong>! 🎉
                    </div>
                    <div class="datetime" id="currentDateTime" style="font-size: 0.95em; color: #555;"></div>
                </div>
            </header>

            <!-- Content area -->
            <div id="contentArea">
                <!-- Existing cards section -->
                <div class="cards">
                    <div class="card">
                        <h3>Total Customers</h3>
                        <p><%= totalCustomers%> customers registered.</p>
                        <a href="#" onclick="loadContent('Customer/Customers.jsp')">View Customers</a>
                    </div>
                    <div class="card">
                        <h3>Today's Sales</h3>
                        <p>LKR <%= String.format("%.2f", todaySales)%></p>
                        <a href="#" onclick="loadContent('Bill/Bills.jsp')">View All Bills</a>
                    </div>
                    <div class="card">
                        <h3>Low Stock Items</h3>
                        <%
                            if (lowStockItems.isEmpty()) {
                        %>
                        <p>All items are sufficiently stocked ✅</p>
                        <%
                        } else {
                        %>
                        <ul>
                            <%
                                int count = 0;
                                for (Item i : lowStockItems) {
                                    if (count >= 3) {
                                        break;
                                    }
                            %>
                            <li><%= i.getTitle()%> (Stock: <%= i.getStock()%>)</li>
                                <%
                                        count++;
                                    }
                                    if (lowStockItems.size() > 3) {
                                %>
                            <li>...and <%= (lowStockItems.size() - 3)%> more</li>
                                <%
                                    }
                                %>
                        </ul>
                        <a href="#" onclick="loadContent('Item/Item.jsp')">Manage Books</a>
                        <%
                            }
                        %>
                    </div>
                </div>

                <!-- 🔹 Recent Transactions Section -->
                <div class="recent-transactions">
                    <h3>Recent Transactions</h3>
                    <div class="table-container" style="max-height: 300px; overflow-y: auto;">
                        <table>
                            <tr>
                                <th>Customer</th>
                                <th>Book Title</th>
                                <th>Units</th>
                                <th>Total (LKR)</th>
                                <th>Date</th>
                            </tr>
                            <%
                                if (bills != null && !bills.isEmpty()) {
                                    // Sort by latest date first (if DAO doesn’t already do this)
                                    bills.sort(( b1,   b2) -> b2.getBillingDate().compareTo(b1.getBillingDate()));

                                    int maxRows = 10; // default max rows; can be tweaked per screen
                                    int rowCount = 0;

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
                                <td><%= cust.getName()%></td>
                                <td><%= itm.getTitle()%></td>
                                <td><%= c.getUnits()%></td>
                                <td><%= String.format("%.2f", c.getTotalAmount())%></td>
                                <td><%= sdf.format(c.getBillingDate())%></td>
                            </tr>
                            <%
                                        rowCount++;
                                        if (rowCount >= maxRows) {
                                            break; // only show up to 10
                                        }
                                    }
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="5">No transactions found.</td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </div>
                </div>
            </div>

        </div>

        <script src="javascript/dashboard.js"></script>
    </body>
</html>
