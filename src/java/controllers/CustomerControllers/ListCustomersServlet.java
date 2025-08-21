package controllers.CustomerControllers;

import DAO.CustomerDAO;
import models.Customer;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ListCustomersServlet")
public class ListCustomersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("search"); // from search bar input
        List<Customer> customers;

        if (keyword != null && !keyword.trim().isEmpty()) {
            customers = CustomerDAO.searchCustomers(keyword);
        } else {
            customers = CustomerDAO.getAllCustomers();
        }

        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/Customer/Customers.jsp").forward(request, response);
    }
}
