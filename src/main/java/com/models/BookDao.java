package com.models;

import java.time.LocalDate;

public class BookDao {

    public int bookID;
    public String bookTitle;
    public String bookAuthor;
    public double bookPrice;
    public int quantity;
    public LocalDate releaseDate;

    public BookDao(int bookID, String bookTitle, String bookAuthor, double bookPrice, int quantity, LocalDate releaseDate) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.quantity = quantity;
        this.releaseDate = releaseDate;
    }

    public String getBookID() {
        return String.valueOf(bookID);
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookPrice() {
        return String.format("%.2f", bookPrice);
    }

    public String getQuantity() {
        return String.valueOf(quantity);
    }

    public String getReleaseDate() {
        return releaseDate.toString();
    }
}
