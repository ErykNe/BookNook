package com.servlets;

import com.models.UserDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && "admin".equals(session.getAttribute("role"))) {
            try {
                List<UserDao> users = getUsers();
                request.setAttribute("users", users);
                request.getRequestDispatcher("userlist.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching the user list.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
        }
    }

    private List<UserDao> getUsers() throws Exception {
        List<UserDao> users = new ArrayList<>();
        Class.forName("org.sqlite.JDBC");
        String path = getServletContext().getRealPath("/WEB-INF/database.db");
        String sql = "SELECT Username, Email, Role FROM Users";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                UserDao user = new UserDao();
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getString("Role"));
                users.add(user);
            }
        }
        return users;
    }
}