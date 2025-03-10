package org.example.debug;

import org.example.data.DynamoDbManager;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


public class DebugDynamoDb {
    public static void main(String[] args) {
        System.out.println("Testing DynamoDB connection...");
        testDynamoDbConnection();
    }

    public static void testDynamoDbConnection() {

        try {
            DynamoDbClient dynamoDb = DynamoDbManager.connect();

            System.out.println("DynamoDb Connection Successful!");
        } catch (Exception e) {
            System.err.println("DynamoDb Connection Failed!: " + e.getMessage());
        }
    }
}
