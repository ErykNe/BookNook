package com.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.DriverManager;

@WebServlet("/OrderDetailsServlet")
public class OrderDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        ArrayList<Map<String, Object>> orderItems = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            String path = getServletContext().getRealPath("/WEB-INF/database.db");
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path)) {
                String sql = "SELECT oi.ItemType, oi.Quantity, " +
                        "CASE WHEN oi.ItemType = 'book' THEN b.BookTitle " +
                        "WHEN oi.ItemType = 'accessory' THEN a.AccessoryName END AS ItemName, " +
                        "CASE WHEN oi.ItemType = 'book' THEN b.BookPrice " +
                        "WHEN oi.ItemType = 'accessory' THEN a.AccessoryPrice END AS ItemPrice " +
                        "FROM OrderItems oi " +
                        "LEFT JOIN Books b ON oi.ItemID = b.BookID AND oi.ItemType = 'book' " +
                        "LEFT JOIN Accessories a ON oi.ItemID = a.AccessoryID AND oi.ItemType = 'accessory' " +
                        "WHERE oi.OrderID = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, orderId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            Map<String, Object> item = new HashMap<>();
                            item.put("ItemType", rs.getString("ItemType"));
                            item.put("Quantity", rs.getInt("Quantity"));
                            item.put("ItemName", rs.getString("ItemName"));
                            item.put("ItemPrice", rs.getDouble("ItemPrice"));
                            orderItems.add(item);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("orderItems", orderItems);
        request.getRequestDispatcher("orderdetails.jsp").forward(request, response);
    }
}