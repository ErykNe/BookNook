package com.Utils;

import com.models.Book;
import com.models.Accessory;

import javax.servlet.ServletContext;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public static ArrayList<Book> getBooks(Connection conn) {
        String sql = "SELECT * FROM Books";
        ArrayList<Book> books = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate through the result set
            while (rs.next()) {
                int id = rs.getInt("BookID");
                String title = rs.getString("BookTitle");
                String author = rs.getString("BookAuthor");
                double price = rs.getDouble("BookPrice");
                int quantity = rs.getInt("Quantity");

                books.add(new Book(id, title, author, price, quantity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static ArrayList<Accessory> getAccessories(Connection conn) {
        String sql = "SELECT * FROM Accessories";
        ArrayList<Accessory> accessories = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                int id = rs.getInt("AccessoryID");
                String name = rs.getString("AccessoryName");
                double price = rs.getDouble("AccessoryPrice");
                int quantity = rs.getInt("Quantity");

                accessories.add(new Accessory(id, name, price, quantity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accessories;
    }

    public static Map<Book, Integer> getBooksByIDs(Connection conn, ArrayList<Integer> bookIds) {
        // Step 1: Count the frequency of each book ID in the list.
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer bookId : bookIds) {
            frequencyMap.put(bookId, frequencyMap.getOrDefault(bookId, 0) + 1);
        }

        // Step 2: For each unique book ID, query the database and store the result in a HashMap.
        Map<Book, Integer> booksWithCount = new HashMap<>();
        String sql = "SELECT * FROM Books WHERE BookID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Loop over the unique book IDs
            for (Integer bookId : frequencyMap.keySet()) {
                // Set the parameter in the prepared statement
                stmt.setInt(1, bookId);
                try (ResultSet rs = stmt.executeQuery()) {
                    // If the book is found, create the BookDao and add it to the map with its count.
                    if (rs.next()) {
                        int id = rs.getInt("BookID");
                        String title = rs.getString("BookTitle");
                        String author = rs.getString("BookAuthor");
                        double price = rs.getDouble("BookPrice");
                        int quantity = rs.getInt("Quantity");


                        Book book = new Book(id, title, author, price, quantity);
                        booksWithCount.put(book, frequencyMap.get(bookId));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Book, Integer> entry : booksWithCount.entrySet()) {
            Book book = entry.getKey();
            Integer frequency = entry.getValue();
        }

        return booksWithCount;
    }
    public static Map<Accessory, Integer> getAccessoriesByIDs(Connection conn, ArrayList<Integer> accessoryIds) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer accessoryId : accessoryIds) {
            frequencyMap.put(accessoryId, frequencyMap.getOrDefault(accessoryId, 0) + 1);
        }

        Map<Accessory, Integer> accessoriesWithCount = new HashMap<>();
        String sql = "SELECT * FROM Accessories WHERE AccessoryID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Integer accessoryId : frequencyMap.keySet()) {
                stmt.setInt(1, accessoryId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("AccessoryID");
                        String name = rs.getString("AccessoryName");
                        double price = rs.getDouble("AccessoryPrice");
                        int quantity = rs.getInt("Quantity");

                        Accessory accessory = new Accessory(id, name, price, quantity);
                        accessoriesWithCount.put(accessory, frequencyMap.get(accessoryId));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return accessoriesWithCount;
    }
}