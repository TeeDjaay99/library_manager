package org.example.data.mysql;

import org.example.models.Borrower;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDb {

    // Retrieves all borrowers from MySQL and returns a list
    public List<Borrower> getAllBorrowers() {
        List<Borrower> borrowers = new ArrayList<>();
        String sql = "SELECT* FROM borrowers";

        try (Connection conn = MysqlConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loops through the result and creates a borrower object
            while (rs.next()) {
                borrowers.add(new Borrower(
                        rs.getInt("borrower_id"),
                        rs.getString("borrower_name"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database error while retrieving borrowers: " + e.getMessage());
        }
        return borrowers;
    }
}
