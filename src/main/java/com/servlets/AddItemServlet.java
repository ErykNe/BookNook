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
        String bookTitle = request.getParameter("bookTitle");
        String bookAuthor = request.getParameter("bookAuthor");
        double bookPrice = Double.parseDouble(request.getParameter("bookPrice"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String releaseDate = request.getParameter("releaseDate");

        try {
            addBook(bookTitle, bookAuthor, bookPrice, quantity, releaseDate);
            request.setAttribute("message", "Book added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while adding the book.");
        }

        request.getRequestDispatcher("additem.jsp").forward(request, response);
    }

    private void addBook(String bookTitle, String bookAuthor, double bookPrice, int quantity, String releaseDate) throws Exception {
        Class.forName("org.sqlite.JDBC");
        String path = getServletContext().getRealPath("/WEB-INF/database.db");
        String sql = "INSERT INTO Books (BookTitle, BookAuthor, BookPrice, Quantity, ReleaseDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookTitle);
            pstmt.setString(2, bookAuthor);
            pstmt.setDouble(3, bookPrice);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, releaseDate);
            pstmt.executeUpdate();
        }
    }
}