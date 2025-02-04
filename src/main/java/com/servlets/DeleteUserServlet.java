package com.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        try {
            Class.forName("org.sqlite.JDBC");
            String path = getServletContext().getRealPath("/WEB-INF/database.db");
            String sql = "DELETE FROM Users WHERE Username = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.executeUpdate();
            }

            request.setAttribute("message", "User deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while deleting the user.");
        }

        request.getRequestDispatcher("/UserListServlet").forward(request, response);
    }
}