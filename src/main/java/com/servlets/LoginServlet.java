package com.servlets;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //default redirect to itself preventing unwanted get actions performed by user which can lead to malfunctions
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isError = false;

        try {
            String role = authenticateUser(username, password);
            //authenticate user by checking its role in the database
            if (role != null) {
                double balance = getUserBalance(username);
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("balance", balance);
                session.setAttribute("role", role);
                request.setAttribute("message", "Login successful!");
                //if no role has been found then send the proper message
            } else {
                request.setAttribute("message", "Invalid username or password.");
                isError = true;
            }
        } catch (Exception e) {
            request.setAttribute("message", "An error occurred during login.");
            isError = true;
            e.printStackTrace();
        }
        //send the message to the page
        request.setAttribute("isError", isError);
        request.getRequestDispatcher("index.jsp?page=login").forward(request, response);
    }

    private String authenticateUser(String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String path = getServletContext().getRealPath("/WEB-INF/database.db");
        String sql = "SELECT Role FROM Users WHERE Username = ? AND Password = ?";
        //execute sql query searching for role of a user with the same username and password
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Role");
                } else {
                    return null;
                }
            }
        }
    }
    private double getUserBalance(String username) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String path = getServletContext().getRealPath("/WEB-INF/database.db");
        String sql = "SELECT Balance FROM Users WHERE Username = ?";
        //execute sql query that gets the balance of a user
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Balance");
                } else {
                    throw new SQLException("User not found");
                }
            }
        }
    }
}