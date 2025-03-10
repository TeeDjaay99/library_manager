package org.example.models;


import java.sql.Date;

public class Loan {

    private int loanId;
    private int bookId;
    private int borrowerId;
    private Date loanDate;
    private Date returnDate;

    public Loan(int loanId, int bookId, int borrowerId, Date loanDate, Date returnDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.borrowerId = borrowerId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
