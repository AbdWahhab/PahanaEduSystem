package controllers.CustomerControllers;

import DAO.CustomerDAO;
import models.Customer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddCustomerServlet")
public class AddCustomerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String account_number = request.getParameter("account_number");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        // Build new user object
        Customer newCustomer = new Customer(account_number, name, address, phone);

        // Register user via DAO
        boolean success = CustomerDAO.registerCustomer(newCustomer);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/Customer/Customers.jsp?message=Registration+successful!");
        } else {
            response.sendRedirect(request.getContextPath() + "/Customer/addCustomer.jsp?message=Registration+failed!");

        }
    }
}
