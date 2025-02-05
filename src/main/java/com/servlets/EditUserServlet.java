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

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String newUsername = request.getParameter("newUsername");
        String newPassword = request.getParameter("newPassword");
        String newEmail = request.getParameter("newEmail");

        try {
            Class.forName("org.sqlite.JDBC");
            String path = getServletContext().getRealPath("/WEB-INF/database.db");
            String sql = "UPDATE Users SET Username = ?, Password = ?, Email = ? WHERE Username = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newUsername);
                pstmt.setString(2, newPassword);
                pstmt.setString(3, newEmail);
                pstmt.setString(4, username);
                pstmt.executeUpdate();
            }

            request.setAttribute("message", "User details updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while updating the user details.");
        }

        request.getRequestDispatcher("/UserListServlet").forward(request, response);
    }
}