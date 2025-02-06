package com.servlets;

import com.models.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    public ArrayList<Book> books = new ArrayList<>();

    public SearchServlet() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String searchStr = request.getParameter("search-input");
        System.out.println(searchStr);
        books = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String path = getServletContext().getRealPath("/WEB-INF/database.db");

        String sql = "SELECT * FROM Books WHERE BookTitle LIKE ? OR BookAuthor LIKE ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchTerm = "%" + searchStr + "%";
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if we have results
                boolean foundResults = false;
                while (rs.next()) {
                    foundResults = true;

                    Book book = new Book(rs.getInt("BookID"), rs.getString("BookTitle")
                    , rs.getString("BookAuthor"), rs.getDouble("BookPrice"), rs.getInt("Quantity"));
                    books.add(book);
                    request.setAttribute("books", books);
                }

                if (!foundResults) {
                    request.setAttribute("books", "noresult");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }
}
