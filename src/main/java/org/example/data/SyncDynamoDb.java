package org.example.data;

import org.example.data.mysql.BookDb;
import org.example.data.mysql.LoanDb;
import org.example.models.Book;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncDynamoDb {

    private final DynamoDbClient dynamoDb;
    private static final String BOOKS_TABLE = "LibraryBooks";
    private static final String LOANS_TABLE ="LibraryLoans";

    public SyncDynamoDb() {
        this.dynamoDb = DynamoDbManager.connect();
    }

    public void syncBooksToDynamoDb() {
        createTableIfNotExists("LibraryBooks", "book_id");

        BookDb bookDb = new BookDb();
        List<Book> books = bookDb.getAllBooks();

        // Creates a HashMap to store the book attribute
        for (Book book : books) {
            Map<String, AttributeValue> item = new HashMap<>();
            item.put("book_id", AttributeValue.builder().n(String.valueOf(book.getBookId())).build());
            item.put("book_title", AttributeValue.builder().s(book.getTitle()).build());
            item.put("author", AttributeValue.builder().s(book.getAuthor()).build());
            item.put("published_year", AttributeValue.builder().n(String.valueOf(book.getPublishedYear())).build());
            item.put("available", AttributeValue.builder().bool(book.isAvailable()).build());

            // Creates a put item request to save in DynamoDb
            PutItemRequest request = PutItemRequest.builder()
                    .tableName(BOOKS_TABLE)
                    .item(item)
                    .build();

            dynamoDb.putItem(request);
            System.out.println("Saved in DynamoDB: " + book.getTitle());
        }
    }

    public void syncLoansToDynamoDb() {
        createTableIfNotExists("LibraryLoans", "loan_id");

        LoanDb loanDb = new LoanDb();
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

        try (var conn = org.example.data.mysql.MysqlConnector.getConnection();
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, AttributeValue> item = new HashMap<>();

                // rs.getstring String.valueOf
                item.put("loan_id", AttributeValue.builder().n(rs.getString("loan_id")).build());
                item.put("book_title", AttributeValue.builder().s(rs.getString("book_title")).build());
                item.put("borrower_name", AttributeValue.builder().s(rs.getString("borrower_name")).build());
                item.put("loan_date", AttributeValue.builder().s(rs.getDate("loan_date").toString()).build());
                item.put("return_date", AttributeValue.builder().s(rs.getDate("return_date") != null ? rs.getDate("return_date").toString() : "Not returned").build());

                PutItemRequest request = PutItemRequest.builder()
                        .tableName(LOANS_TABLE)
                        .item(item)
                        .build();

                dynamoDb.putItem(request);
                System.out.println("Saved in DynamoDB (loans): " + rs.getString("book_title") + " borrowed by: " + rs.getString("borrower_name"));
            }
        } catch (Exception e) {
            System.err.println("Error in synchronizing loans: " + e.getMessage());
        }
    }

    private void createTableIfNotExists(String tableName, String partitionKey) {
        try {
            var dynamoDb = DynamoDbManager.connect();

            var listTableRequest = ListTablesRequest.builder().build();
            var existingTables = dynamoDb.listTables(listTableRequest).tableNames();

            if (!existingTables.contains(tableName)) {
                System.out.println("Creating table: " + tableName);

                var createTableRequest = CreateTableRequest.builder()
                        .tableName(tableName)
                        .keySchema(KeySchemaElement.builder().attributeName(partitionKey).keyType(KeyType.HASH).build())
                        .attributeDefinitions(AttributeDefinition.builder().attributeName(partitionKey).attributeType("N").build())
                        .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                        .build();

                dynamoDb.createTable(createTableRequest);
                System.out.println("Table created: " + tableName);
            }
        } catch (Exception e) {
            System.err.println("Could not create table: " + tableName + ": " + e.getMessage());
        }
    }
}
