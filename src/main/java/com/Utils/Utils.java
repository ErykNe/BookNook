package com.Utils;

import com.models.BookDao;

import javax.servlet.ServletContext;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Utils {


    public static Connection getConnection(ServletContext context) {
        Connection conn = null;
        try {
            String dbPath = context.getRealPath("/WEB-INF/database.db");
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static ArrayList<BookDao> getBooks(Connection conn) {
        String sql = "SELECT * FROM Books";
        ArrayList<BookDao> books = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

            // Iterate through the result set
            while (rs.next()) {
                int id = rs.getInt("BookID");
                String title = rs.getString("BookTitle");
                String author = rs.getString("BookAuthor");
                double price = rs.getDouble("BookPrice");
                int quantity = rs.getInt("Quantity");

                // Parse the release date using the custom formatter
                LocalDate releaseDate = null;
                String dateString = rs.getString("ReleaseDate");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("y-MM-dd");
                if (dateString != null && !dateString.isEmpty()) {
                    try {
                        releaseDate = LocalDate.parse(dateString, formatter);
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                    }
                }

                books.add(new BookDao(id, title, author, price, quantity, releaseDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
}
