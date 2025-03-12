package org.example.data.mysql;

import org.example.models.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDb {

    // Retrieves all books from MySQL and returns a list
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = MysqlConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

           // Loops through the result and creates a book object
           while (rs.next()) {
               books.add(new Book(
                       rs.getInt("book_id"),
                       rs.getString("book_title"),
                       rs.getString("author"),
                       rs.getInt("published_year"),
                       rs.getBoolean("available")
               ));
           }
        } catch (SQLException e) {
            System.err.println("Database error when retrieving books: " + e.getMessage());
        }
        return books;
    }
}
