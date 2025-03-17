package org.example.models;

public class Book {

    private int bookId;
    private String title;
    private String author;
    private int publishedYear;
    private boolean available;

    public Book(int bookId, String title, String author, int publishedYear, boolean available) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.available = available;
    }

    /* I chose to only use Getter because i felt like since im retrieving data from a pre-made MySQL database
    and not Setting it, it made more sense to me to only use Getters but i can be completely wrong in choosing
    to do it this way.
     */

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public boolean isAvailable() {
        return available;
    }
}
