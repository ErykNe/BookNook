package com.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.ResultSet;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        ArrayList<Integer> bookCart = (ArrayList<Integer>) session.getAttribute("bookCart");
        ArrayList<Integer> accessoriesCart = (ArrayList<Integer>) session.getAttribute("accessoriesCart");

        if (username == null || (bookCart == null && accessoriesCart == null)) {
            response.sendRedirect("index.jsp?page=cart");
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            String path = getServletContext().getRealPath("/WEB-INF/database.db");
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path)) {
                conn.setAutoCommit(false);

                String orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String insertOrderSQL = "INSERT INTO Orders (Username, OrderDate) VALUES (?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, orderDate);
                    pstmt.executeUpdate();

                    int orderId;
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            orderId = rs.getInt(1);
                        } else {
                            throw new Exception("Failed to retrieve order ID.");
                        }
                    }

                    String insertOrderItemSQL = "INSERT INTO OrderItems (OrderID, ItemType, ItemID, Quantity) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmtItem = conn.prepareStatement(insertOrderItemSQL)) {
                        if (bookCart != null) {
                            for (Integer bookId : bookCart) {
                                pstmtItem.setInt(1, orderId);
                                pstmtItem.setString(2, "book");
                                pstmtItem.setInt(3, bookId);
                                pstmtItem.setInt(4, 1); // Assuming quantity is 1 for simplicity
                                pstmtItem.addBatch();
                            }
                        }
                        if (accessoriesCart != null) {
                            for (Integer accessoryId : accessoriesCart) {
                                pstmtItem.setInt(1, orderId);
                                pstmtItem.setString(2, "accessory");
                                pstmtItem.setInt(3, accessoryId);
                                pstmtItem.setInt(4, 1); // Assuming quantity is 1 for simplicity
                                pstmtItem.addBatch();
                            }
                        }
                        pstmtItem.executeBatch();
                    }

                    conn.commit();
                }

                session.removeAttribute("bookCart");
                session.removeAttribute("accessoriesCart");
                request.setAttribute("message", "Order placed successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while placing the order.");
        }

        request.getRequestDispatcher("index.jsp?page=orders").forward(request, response);
    }
}