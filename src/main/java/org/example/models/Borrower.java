package org.example.models;

public class Borrower {

    private int borrowerId;
    private String name;
    private String email;

    public Borrower(int borrowerId, String name, String email) {
        this.borrowerId = borrowerId;
        this.name = name;
        this.email = email;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
