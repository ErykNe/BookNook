package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemType = request.getParameter("itemType");
        String message = "";

        try {
            if ("book".equalsIgnoreCase(itemType)) {
                String bookTitle = request.getParameter("bookTitle");
                String bookAuthor = request.getParameter("bookAuthor");
                double bookPrice = Double.parseDouble(request.getParameter("bookPrice"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));


                addBook(bookTitle, bookAuthor, bookPrice, quantity);
                message = "Book added successfully!";
            } else if ("accessory".equalsIgnoreCase(itemType)) {
                String accessoryName = request.getParameter("accessoryName");
                double accessoryPrice = Double.parseDouble(request.getParameter("accessoryPrice"));
                int accessoryQuantity = Integer.parseInt(request.getParameter("accessoryQuantity"));

                System.out.println("Accessory Name: " + accessoryName);
                System.out.println("Accessory Price: " + accessoryPrice);
                System.out.println("Quantity: " + accessoryQuantity);

                addAccessory(accessoryName, accessoryPrice, accessoryQuantity);
                message = "Accessory added successfully!";
            } else {
                message = "Invalid item type!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while adding the item.";
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("additem.jsp").forward(request, response);
    }

    private void addBook(String bookTitle, String bookAuthor, double bookPrice, int quantity) throws Exception {
        Class.forName("org.sqlite.JDBC");
        String path = getServletContext().getRealPath("/WEB-INF/database.db");
        String sql = "INSERT INTO Books (BookTitle, BookAuthor, BookPrice, Quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookTitle);
            pstmt.setString(2, bookAuthor);
            pstmt.setDouble(3, bookPrice);
            pstmt.setInt(4, quantity);
            pstmt.executeUpdate();
        }
    }

    private void addAccessory(String accessoryName, double accessoryPrice, int quantity) throws Exception {
        Class.forName("org.sqlite.JDBC");
        String path = getServletContext().getRealPath("/WEB-INF/database.db");
        String sql = "INSERT INTO Accessories (AccessoryName, AccessoryPrice, Quantity) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accessoryName);
            pstmt.setDouble(2, accessoryPrice);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        }
    }
}