package org.example.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.err;

public class LoanDb {

    public void getAllLoansWithDetails() {
        String sql = """
                SELECT
                	loans.loan_id,
                	books.book_title AS book_title,
                	borrowers.borrower_name AS borrower_name,
                	loans.loan_date,
                	loans.return_date
                FROM loans
                JOIN books ON loans.book_id = books.book_id
                JOIN borrowers ON loans.borrower_id = borrowers.borrower_id
                """;

        try (Connection conn = MysqlConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n Active loans:");
            while (rs.next()) {
                System.out.println(" Loan-ID: " + rs.getInt("loan_id") +
                        " | Book: " + rs.getString("book_title") +
                        " | Borrower: " + rs.getString("borrower_name") +
                        " | Loaned: " + rs.getDate("loan_date") +
                        " | Returned: " + (rs.getDate("return_date") != null ? rs.getDate("return_date") : "Not returned"));
            }
        } catch (SQLException e) {
            System.err.println("Error when retrieving loans: " + e.getMessage());
        }
    }
}
