package controllers.ItemController;

import DAO.ItemDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Item;

@WebServlet("/ViewItemServlet")
public class ViewItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // get id from request (now int, not code)
            String code = request.getParameter("code");

            // use updated DAO
            Item item = ItemDAO.filterItemById(code);

            if (item != null) {
                request.setAttribute("item", item);
                request.getRequestDispatcher("/Item/Item.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/Item/Item.jsp?message=Item+not+found!");
            }
        } catch (NumberFormatException e) {
            // invalid or missing id
            response.sendRedirect(request.getContextPath() + "/Item/Item.jsp?message=Invalid+Item+Code!");
        }
    }
}
