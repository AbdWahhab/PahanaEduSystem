package controllers.BillController;

import DAO.BillDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Bill;

@WebServlet("/ListBillServlet")
public class ListBillServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Bill> bills;

        bills = BillDAO.getAllBills();

        request.setAttribute("bills", bills);
        request.getRequestDispatcher("/Customer/Customers.jsp").forward(request, response);
    }
}
