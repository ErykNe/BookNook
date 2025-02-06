package com.servlets;

import com.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get the user that is being edited by admin
        String username = request.getParameter("username");
        User user = null;

        try {
            Class.forName("org.sqlite.JDBC");
            String path = getServletContext().getRealPath("/WEB-INF/database.db");
            String sql = "SELECT * FROM Users WHERE Username = ?";
            //execute the query that gets all the data about the user
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        user = new User();

                        user.setUsername(rs.getString("Username"));
                        user.setPassword(rs.getString("Password"));
                        user.setEmail(rs.getString("Email"));
                        user.setRole(rs.getString("Role"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user != null) {
            //send the data to the page
            request.setAttribute("user", user);
            request.getRequestDispatcher("/edituser.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String newUsername = request.getParameter("newUsername");
        String newPassword = request.getParameter("newPassword");
        String newEmail = request.getParameter("newEmail");
        String newRole = request.getParameter("newRole");

        try {
            Class.forName("org.sqlite.JDBC");
            String path = getServletContext().getRealPath("/WEB-INF/database.db");
            String sql = "UPDATE Users SET Username = ?, Password = ?, Email = ?, Role = ? WHERE Username = ?";
            //execute query updating data of user
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newUsername);
                pstmt.setString(2, newPassword);
                pstmt.setString(3, newEmail);
                pstmt.setString(4, newRole);
                pstmt.setString(5, username);

                int rowsUpdated = pstmt.executeUpdate();
                //send the message to the page
                if (rowsUpdated > 0) {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}