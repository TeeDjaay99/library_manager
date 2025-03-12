package org.example;

import org.example.data.SyncDynamoDb;
import org.example.data.mysql.BookDb;
import org.example.data.mysql.LoanDb;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n Library system - Main menu");
            System.out.println("1. Show all books");
            System.out.println("2. Show all loans");
            System.out.println("3. Sync data to DynamoDb");
            System.out.println("4. Exit");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showBooks();
                    break;
                case 2:
                    showLoans();
                    break;
                case 3:
                    syncToDynamoDb();
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, Try again!");
                    break;
            }

        }
    }

    // Retrieves and shows all books from MySQL
    private static void showBooks() {
        BookDb bookDb = new BookDb();
        bookDb.getAllBooks().forEach(book -> System.out.println("book: " + book.getTitle() + " by " + book.getAuthor()));
    }

    // Retrieves all loans from MySQL
    private static void showLoans() {
        LoanDb loanDb = new LoanDb();
        loanDb.getAllLoansWithDetails();
    }

    // Syncs both books and loans from MySQL to DynamoDb
    private static void syncToDynamoDb() {
        SyncDynamoDb syncDynamoDb = new SyncDynamoDb();
        syncDynamoDb.syncBooksToDynamoDb();
        syncDynamoDb.syncLoansToDynamoDb();
        System.out.println("Data synced to DynamoDB!");
    }
}