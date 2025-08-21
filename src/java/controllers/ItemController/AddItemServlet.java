package controllers.ItemController;

import DAO.ItemDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Item;

@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data (Strings for text fields)
        String code = request.getParameter("code");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");

        // Parse numeric values safely
        float price = 0.0f;
        int stock = 0;
        try {
            String priceParam = request.getParameter("price");
            if (priceParam != null && !priceParam.isEmpty()) {
                price = Float.parseFloat(priceParam);
            }

            String stockParam = request.getParameter("stock");
            if (stockParam != null && !stockParam.isEmpty()) {
                stock = Integer.parseInt(stockParam);
            }
        } catch (NumberFormatException e) {
            // Log and optionally set an error message
            e.printStackTrace();
            response.sendRedirect("addItem.jsp?message=Invalid+number+format!");
            return;
        }

        // Build new item object (constructor must accept float + int)
        Item newItem = new Item(code, title, description, category, price, stock);

        // Register item via DAO
        boolean success = ItemDAO.addItem(newItem);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/Item/Item.jsp?message=Registration+successful!");
        } else {
            response.sendRedirect(request.getContextPath() + "/Item/addItem.jsp?message=Registration+failed!");

        }
    }

}
