package com.models;

public class Book extends Item {

    public int bookID;
    private String Author;

    public Book(int bookID, String bookTitle, String author, double bookPrice, int quantity) {
        super(bookTitle, bookPrice,quantity);
        this.bookID = bookID;
        this.Author = author;
    }

    public String getBookID() {
        return String.valueOf(bookID);
    }

    public String getAuthor() {
        return Author;
    }

}