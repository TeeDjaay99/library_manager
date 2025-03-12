package org.example.debug;

import org.example.data.mysql.MysqlConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DebugMysql {

    public static void main(String[] args) {
        System.out.println("Testing MySQL connection...");
        testMysqlConnection();
    }

    public static void testMysqlConnection() {
        try (Connection conn = MysqlConnector.getConnection()) {
            if (conn != null) {
                System.out.println("MySQL-connection success!");

                String sql = "SELECT book_id, book_title, author FROM books";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    // Loops through the rows/columns and writes out the information
                    System.out.println("books in the database:");
                    while (rs.next()) {
                        System.out.println("Book-ID: " + rs.getInt("book_id") +
                                " | Title: " + rs.getString("book_title") +
                                " | Author: " + rs.getString("author"));
                    }
                }
            } else {
                System.out.println("MySQL-connection failed!");
            }
        } catch (SQLException e) {
            // Catches error if something goes wrong with the database connection
            System.err.println("MySql-error: " + e.getMessage());
        }
    }
}
