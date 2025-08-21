package controllers;

import utils.PasswordUtil;
import DAO.UserDAO;
import models.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = PasswordUtil.hashPassword(request.getParameter("password"));
        String role = request.getParameter("role");

        // Build new user object
        User newUser = new User(username, email, password, role);

        // Register user via DAO
        boolean success = UserDAO.registerUser(newUser);

        if (success) {
            response.sendRedirect("register.jsp?message=Registration+successful!");
        } else {
            response.sendRedirect("register.jsp?message=Registration+failed!");
        }
    }
}
