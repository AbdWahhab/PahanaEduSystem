/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.CustomerControllers;

import DAO.CustomerDAO;
import models.Customer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateCustomerServlet")
public class UpdateCustomerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String account_number = request.getParameter("account_number");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        Customer customer = new Customer(account_number, name, address, phone);
        boolean success = CustomerDAO.updateCustomer(customer); // implement this in DAO

        if (success) {
            response.sendRedirect(request.getContextPath() + "/ListCustomersServlet?message=Update+successful!");
        } else {
            response.sendRedirect(request.getContextPath() + "/Customer/editCustomer.jsp?message=Update+failed!");
        }
    }
}