package controllers.ItemController;

import DAO.ItemDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteItemServlet")
public class DeleteItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        if (code != null && !code.trim().isEmpty()) {
            boolean deleted = ItemDAO.deleteItem(code);

            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/Item/Item.jsp?msg=deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/Item/Item.jsp?error=deletefailed");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/Item/Item.jsp?error=invalidcode");
        }
    }
}
