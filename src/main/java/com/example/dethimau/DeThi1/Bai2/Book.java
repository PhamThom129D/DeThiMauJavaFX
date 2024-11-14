package com.example.dethimau;

public class Book {
    private int idBook;
    private String nameBook;
    private String authorBook;
    private int priceBook;
    private boolean statusBook;
    public Book() {}
    public Book(int idBook, String nameBook, String authorBook, int priceBook, boolean statusBook) {
        this.idBook = idBook;
        this.nameBook = nameBook;
        this.authorBook = authorBook;
        this.priceBook = priceBook;
        this.statusBook = statusBook;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(String authorBook) {
        this.authorBook = authorBook;
    }

    public int getPriceBook() {
        return priceBook;
    }

    public void setPriceBook(int priceBook) {
        this.priceBook = priceBook;
    }

    public boolean isStatusBook() {
        return statusBook;
    }

    public void setStatusBook(boolean statusBook) {
        this.statusBook = statusBook;
    }
}
