package com.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateCartServlet() {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp?page=cart").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookID = -1;
        int bookQuantity = -1;
        if (request.getParameter("BookID") != null) {
            bookID = Integer.parseInt(request.getParameter("BookID"));
        }
        if (request.getParameter("bookQuantity") != null) {
            bookQuantity = Integer.parseInt(request.getParameter("bookQuantity"));
        }

        int accessoryID = -1;
        int accessoryQuantity = -1;
        if (request.getParameter("AccessoryID") != null) {
            accessoryID = Integer.parseInt(request.getParameter("AccessoryID"));
        }
        if (request.getParameter("accessoryQuantity") != null) {
            accessoryQuantity = Integer.parseInt(request.getParameter("accessoryQuantity"));
        }

        HttpSession session = request.getSession();
        ArrayList<Integer> bookIDs = (ArrayList<Integer>) session.getAttribute("bookCart");
        ArrayList<Integer> accessoryIDs = (ArrayList<Integer>) session.getAttribute("accessoriesCart");

        if (bookID != -1) {
            updateCart(bookIDs, bookID, bookQuantity);
            session.setAttribute("bookCart", bookIDs);
        }
        if (accessoryID != -1) {
            updateCart(accessoryIDs, accessoryID, accessoryQuantity);
            session.setAttribute("accessoriesCart", accessoryIDs);
        }

        // Logic to handle order placement
        if (request.getParameter("placeOrder") != null) {
            logger.info("placeOrder parameter is present. Calling placeOrder method.");
            placeOrder(session);
        } else {
            logger.info("placeOrder parameter is not present.");
        }

        request.getRequestDispatcher("/index.jsp?page=cart").forward(request, response);
    }

    private void updateCart(ArrayList<Integer> cart, int itemID, int itemQuantity) {
        int count = 0;
        for (Integer id : cart) {
            if (id.equals(itemID)) {
                count++;
            }
        }

        if (count > itemQuantity) {
            int removeCount = count - itemQuantity;
            for (int i = cart.size() - 1; i >= 0 && removeCount > 0; i--) {
                if (cart.get(i).equals(itemID)) {
                    cart.remove(i);
                    removeCount--;
                }
            }
        } else if (count < itemQuantity) {
            int addCount = itemQuantity - count;
            for (int i = 0; i < addCount; i++) {
                cart.add(itemID);
            }
        }
    }
    private static final Logger logger = Logger.getLogger(UpdateCartServlet.class.getName());
    private void placeOrder(HttpSession session) {
        ArrayList<Integer> bookIDs = (ArrayList<Integer>) session.getAttribute("bookCart");
        ArrayList<Integer> accessoryIDs = (ArrayList<Integer>) session.getAttribute("accessoriesCart");

        try {
            Class.forName("org.sqlite.JDBC");
            String path = getServletContext().getRealPath("/WEB-INF/database.db");
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path)) {
                if (bookIDs != null) {
                    for (Integer bookID : bookIDs) {
                        String sql = "UPDATE Books SET Quantity = Quantity - 1 WHERE BookID = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setInt(1, bookID);
                            int rowsUpdated = pstmt.executeUpdate();
                            logger.info("Updated BookID: " + bookID + ", Rows affected: " + rowsUpdated);
                        }
                    }
                }

                if (accessoryIDs != null) {
                    for (Integer accessoryID : accessoryIDs) {
                        String sql = "UPDATE Accessories SET Quantity = Quantity - 1 WHERE AccessoryID = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setInt(1, accessoryID);
                            int rowsUpdated = pstmt.executeUpdate();
                            logger.info("Updated AccessoryID: " + accessoryID + ", Rows affected: " + rowsUpdated);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Error updating quantities: " + e.getMessage());
            e.printStackTrace();
        }

        session.removeAttribute("bookCart");
        session.removeAttribute("accessoriesCart");
    }
}