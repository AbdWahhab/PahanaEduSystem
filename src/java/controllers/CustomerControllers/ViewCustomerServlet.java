package controllers.CustomerControllers;

import DAO.CustomerDAO;
import models.Customer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewCustomerServlet")
public class ViewCustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accountNumber = request.getParameter("id");
        Customer customer = CustomerDAO.filterCustomerById(accountNumber);

        if (customer != null) {
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("/Customer/Customer.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/Customer/Customers.jsp?message=Customer+not+found!");
        }
    }
}