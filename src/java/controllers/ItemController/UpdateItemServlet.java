package controllers.ItemController;

import DAO.ItemDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Item;

@WebServlet("/UpdateItemServlet")
public class UpdateItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        float price = Float.parseFloat(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Item item = new Item(code, title, description, category, price, stock);
        boolean success = ItemDAO.updateItem(item); // implement this in DAO

        if (success) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp?message=Update+successful!");
        } else {
            response.sendRedirect(request.getContextPath() + "/Item/editItem.jsp?message=Update+failed!");
        }
    }
}
