package com.servlets;

import com.models.AccessoryDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public OrderServlet() {}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp?page=cart").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String username = session.getAttribute("username").toString();

        double totalPrice = (double) session.getAttribute("totalPrice");
        double userBalance = (double) session.getAttribute("balance");
        totalPrice = Double.parseDouble(String.format("%.2f", totalPrice));
        userBalance = Double.parseDouble(String.format("%.2f", userBalance));

        ArrayList<Integer> bookIDs = (ArrayList<Integer>) session.getAttribute("bookCart");
        ArrayList<Integer> accessoryIDs = (ArrayList<Integer>) session.getAttribute("accessoriesCart");

        System.out.println("Order on: " + username + " Total price of order: " + totalPrice);
        String orderDetails = "Books: ";
        for(Integer bookID : bookIDs) {
            orderDetails += bookID.toString() + " ";
        }
        orderDetails += " Accessories: ";
        for(Integer accessoryID : accessoryIDs) {
            orderDetails += accessoryID.toString() + " ";
        }
        System.out.println(orderDetails);

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            request.setAttribute("messageOrder", "Transaction failed. Please try again.");
            throw new RuntimeException(e);
        }

        int userId = -1;
        try {
            userId = getUserId(username, getServletContext());
        } catch (ClassNotFoundException | SQLException e) {
            request.setAttribute("messageOrder", "Transaction failed. Please try again.");
            throw new RuntimeException(e);

        }

        if(userId != -1) {
            if(userBalance < totalPrice) {
                request.setAttribute("messageOrder", "Transaction failed. Insufficient funds.");
            } else {
                try {
                    addOrder(userId, orderDetails, getServletContext());
                    updateBooksQuantity(bookIDs, getServletContext());
                    updateAccessoriesQuantity(accessoryIDs, getServletContext());
                    updateUserBalance(userId, totalPrice, getServletContext(), session);
                } catch (ClassNotFoundException | SQLException e) {
                    request.setAttribute("messageOrder", "Transaction failed. Please try again.");
                    throw new RuntimeException(e);
                }
            }
        }

        request.setAttribute("messageOrder", "Transaction completed. Thank you for your order!");
        session.setAttribute("bookCart", null);
        session.setAttribute("accessoriesCart", null);

        request.getRequestDispatcher("index.jsp?page=cart").forward(request, response);

    }
    protected int getUserId(String username, ServletContext context) throws ClassNotFoundException, SQLException {
        int id = -1;
        String path = context.getRealPath("/WEB-INF/database.db");
        String sql = "SELECT UserID FROM Users WHERE Username = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("UserID");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    protected void addOrder(int userId, String orderDetails, ServletContext context) throws ClassNotFoundException, SQLException {
        String path = context.getRealPath("/WEB-INF/database.db");
        String sql = "INSERT INTO Orders (UserID, OrderDetails) VALUES (?, ?);";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, orderDetails);

            pstmt.executeUpdate();
        }
    }
    protected void updateBooksQuantity(ArrayList<Integer> bookIDs, ServletContext context) throws ClassNotFoundException, SQLException {
        String path = context.getRealPath("/WEB-INF/database.db");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path)) {

            String sql = "UPDATE Books SET Quantity = Quantity - 1 WHERE BookID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (Integer bookId : bookIDs) {
                    stmt.setInt(1, bookId);
                    stmt.executeUpdate();
                }
            }
        }
    }
    protected void updateAccessoriesQuantity(ArrayList<Integer> accessoryIDs, ServletContext context) throws ClassNotFoundException, SQLException {
        String path = context.getRealPath("/WEB-INF/database.db");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path)) {

            String sql = "UPDATE Accessories SET Quantity = Quantity - 1 WHERE AccessoryID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (Integer accessoryId : accessoryIDs) {
                    stmt.setInt(1, accessoryId);
                    stmt.executeUpdate();
                }
            }
        }
    }
    protected void updateUserBalance(int userID, double price, ServletContext context, HttpSession session) throws ClassNotFoundException, SQLException {
        String path = context.getRealPath("/WEB-INF/database.db");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path)) {
            String sql = "UPDATE Users SET Balance = Balance - ? WHERE UserID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, price);
                stmt.setInt(2, userID);
                stmt.executeUpdate();
            }
            double oldUserBalance = 0;
            String sql2 = "SELECT Balance FROM Users WHERE UserID = ?";
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                stmt2.setInt(1, userID);
                try (ResultSet rs = stmt2.executeQuery()) {
                    if (rs.next()) {
                        oldUserBalance = rs.getDouble("Balance");
                    }
                }
            }
            session.setAttribute("balance", oldUserBalance - price);
        }

    }
}