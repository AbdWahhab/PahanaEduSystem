package controllers.BillController;

import DAO.BillDAO;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Bill;
import utils.BillResult;

@WebServlet("/UpdateBillServlet")
public class UpdateBillServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int customerId = Integer.parseInt(request.getParameter("customer_id"));
            int itemId = Integer.parseInt(request.getParameter("item_id"));
            int units = Integer.parseInt(request.getParameter("units"));
            float unitPrice = Float.parseFloat(request.getParameter("unit_price"));
            float totalAmount = Float.parseFloat(request.getParameter("total_amount"));

            // Parse billing date (assuming yyyy-MM-dd from form)
            String billingDateStr = request.getParameter("billing_date");
            Timestamp billingDate = Timestamp.valueOf(billingDateStr + " 00:00:00");

            // Build Bill object
            Bill updatedBill = new Bill(customerId, itemId, units, unitPrice, totalAmount, billingDate);

            // Call DAO
            BillResult result = BillDAO.updateBill(updatedBill);

            if (result.isSuccess()) {
                response.sendRedirect(request.getContextPath() + "/ListCustomersServlet?message="
                        + result.getMessage());
            } else {
                response.sendRedirect(request.getContextPath() + "/Customer/editCustomer.jsp?error="
                        + result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Customer/editCustomer.jsp?error=Error+processing+request!");
        }
    }
}
