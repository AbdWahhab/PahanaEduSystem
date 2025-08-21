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

@WebServlet("/CreateBillServlet")
public class CreateBillServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get form data
            String customerIdStr = request.getParameter("customer_id");
            String itemIdStr = request.getParameter("item_id");
            String unitsStr = request.getParameter("units");
            String unitPriceStr = request.getParameter("unit_price");
            String totalAmountStr = request.getParameter("total_amount");
            String billingDateStr = request.getParameter("billing_date");

            System.out.println("DEBUG - Received form data:");
            System.out.println("customer_id: " + customerIdStr);
            System.out.println("item_id: " + itemIdStr);
            System.out.println("units: " + unitsStr);
            System.out.println("unit_price: " + unitPriceStr);
            System.out.println("total_amount: " + totalAmountStr);
            System.out.println("billing_date: " + billingDateStr);

            int customerId = Integer.parseInt(customerIdStr);
            int itemId = Integer.parseInt(itemIdStr);
            int units = Integer.parseInt(unitsStr);
            float unitPrice = Float.parseFloat(unitPriceStr);
            float totalAmount = Float.parseFloat(totalAmountStr);

            Timestamp billingDate = Timestamp.valueOf(billingDateStr + " 00:00:00");

            // Build Bill object
            Bill newBill = new Bill(customerId, itemId, units, unitPrice, totalAmount, billingDate);

            // Save via DAO
            BillResult result = BillDAO.createBill(newBill);

            if (result.isSuccess()) {
                response.sendRedirect(request.getContextPath() + "/Bill/Bill.jsp?message="
                        + result.getMessage());
            } else {
                response.sendRedirect(request.getContextPath() + "/Bill/createBill.jsp?error="
                        + result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Bill/createBill.jsp?error=Error+processing+request!");
        }
    }
}
