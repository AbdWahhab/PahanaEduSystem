<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Help - Pahana Edu Billing System</title>
        <style>
/*            body {
                font-family: Arial, sans-serif;
                margin: 30px;
                line-height: 1.6;
            }*/
            h2 {
                color: #2c3e50;
            }
            ul {
                margin-left: 20px;
            }
            .section {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <h2>Help - Pahana Edu Billing System</h2>
        <div class="section">
            <p>Welcome to the Pahana Edu Billing System. This guide will help you understand how to use the system effectively.</p>
        </div>

        <div class="section">
            <h3>1. Login</h3>
            <p>Enter your <b>username</b> and <b>password</b> to access the system.</p>
        </div>

        <div class="section">
            <h3>2. Customer Management</h3>
            <ul>
                <li><b>Add New Customer:</b> Enter account number, name, address, telephone number</li>
                <li><b>Edit Customer:</b> Select a customer and update details.</li>
                <li><b>View Details:</b> Search or browse to see full account information.</li>
            </ul>
        </div>

        <div class="section">
            <h3>3. Item Management</h3>
            <p>You can <b>add</b>, <b>update</b>, or <b>delete</b> items from the inventory. Ensure item codes are unique.</p>
        </div>

        <div class="section">
            <h3>4. Billing</h3>
            <p>The system calculates the bill based on <b>units consumed × unit price</p>
        </div>

        <div class="section">
            <h3>5. Navigation</h3>
            <p>Use the sidebar to move between different modules (Customers, Items, Bills, Help, Logout).</p>
        </div>

        <div class="section">
            <h3>6. Validations</h3>
            <ul>
                <li>Account number must be unique.</li>
                <li>Telephone number should contain digits only.</li>
                <li>All mandatory fields must be filled before saving.</li>
            </ul>
        </div>

        <div class="section">
            <h3>7. Logout</h3>
            <p>Safe logout with confirmation</p>
        </div>
    </body>
</html>
