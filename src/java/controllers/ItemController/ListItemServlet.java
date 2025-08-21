package controllers.ItemController;

import DAO.ItemDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Item;

@WebServlet("/ListItemServlet")
public class ListItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("search"); // from search bar input
        List<Item> items;

        if (keyword != null && !keyword.trim().isEmpty()) {
            items = ItemDAO.searchItems(keyword);
        } else {
            items = ItemDAO.getAllItems();
        }

        request.setAttribute("items", items);
        request.getRequestDispatcher("/Item/Item.jsp").forward(request, response);
    }
}
