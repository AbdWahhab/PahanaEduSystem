<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="models.User, models.Customer, models.Item, DAO.CustomerDAO, DAO.ItemDAO, java.util.List" %>
<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp?error=notloggedin");
        return;
    }

    // Load customers and items for dropdowns
    List<Customer> customers = CustomerDAO.getAllCustomers();
    List<Item> items = ItemDAO.getAllItems();

    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(new java.util.Date());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Billing</title>
        <link rel="stylesheet" href="../css/form.css">
        <script>
            let itemsData = {};
            <% for (Item item : items) {%>
            itemsData["<%=item.getId()%>"] = {
                price: <%=item.getPrice()%>,
                stock: <%=item.getStock()%>
            };
            <% } %>

            function updateItemDetails() {
                const itemSelect = document.getElementById("item_id");
                const unitPriceInput = document.getElementById("unit_price");
                const unitsInput = document.getElementById("units");
                const totalAmountInput = document.getElementById("total_amount");
                const stockInfo = document.getElementById("stock_info");

                const itemId = itemSelect.value;
                if (itemId && itemsData[itemId]) {
                    unitPriceInput.value = itemsData[itemId].price;
                    stockInfo.textContent = "Available stock: " + itemsData[itemId].stock;
                    updateTotal();
                } else {
                    unitPriceInput.value = "";
                    totalAmountInput.value = "";
                    stockInfo.textContent = "";
                }
            }

            function updateTotal() {
                const itemSelect = document.getElementById("item_id");
                const units = parseInt(document.getElementById("units").value) || 0;
                const unitPrice = parseFloat(document.getElementById("unit_price").value) || 0;
                const totalAmountInput = document.getElementById("total_amount");
                const stockInfo = document.getElementById("stock_info");

                if (itemSelect.value && itemsData[itemSelect.value]) {
                    const stock = itemsData[itemSelect.value].stock;
                    if (units > stock) {
                        alert("You cannot order more than the available stock (" + stock + ").");
                        document.getElementById("units").value = stock;
                        totalAmountInput.value = (stock * unitPrice).toFixed(2);
                    } else {
                        totalAmountInput.value = (units * unitPrice).toFixed(2);
                    }
                }
            }

        </script>
    </head>
    <body>
        <div class="register-container">
            <h2>Create Bill</h2>
            <%
                String message = request.getParameter("message");
                if (message != null) {
            %>
            <p class="message"><%= message%></p>
            <% }%>

            <form action="<%= request.getContextPath()%>/CreateBillServlet" method="post">

                <!-- Select Customer -->
                <label for="customer_id">Select Customer</label>
                <select name="customer_id" id="customer_id" required>
                    <option value="">Choose Customer</option>
                    <% for (Customer c : customers) {%>
                    <option value="<%=c.getId()%>">
                        <%= c.getAccountNumber()%> - <%= c.getName()%>
                    </option>
                    <% } %>
                </select>

                <!-- Select Item -->
                <label for="item_id">Select Book</label>
                <select name="item_id" id="item_id" onchange="updateItemDetails()" required>
                    <option value="">Choose Book</option>
                    <% for (Item i : items) {%>
                    <option value="<%=i.getId()%>">
                        <%= i.getCode()%> - <%= i.getTitle()%>
                    </option>
                    <% }%>
                </select>
                <div id="stock_info" class="stock-info"></div>

                <!-- Units -->
                <label for="units">Units</label>
                <input type="number" id="units" name="units" min="1" required oninput="updateTotal()">

                <!-- Unit Price -->
                <label for="unit_price">Unit Price</label>
                <input type="text" id="unit_price" name="unit_price" readonly>

                <!-- Total Amount -->
                <label for="total_amount">Total Amount</label>
                <input type="text" id="total_amount" name="total_amount" readonly>

                <!-- Billing Date -->
                <label for="billing_date">Billing Date</label>
                <input type="date" id="billing_date" name="billing_date" value="<%= today%>" min="<%= today%>" max="<%= today%>" required>

                <script>
                    document.querySelector("form").addEventListener("submit", function (e) {
                        console.log("Submitting customer_id:", document.getElementById("customer_id").value);
                        console.log("Submitting item_id:", document.getElementById("item_id").value);
                    });
                </script>

                <input type="submit" value="Create Bill">
            </form>
        </div>
    </body>
</html>
