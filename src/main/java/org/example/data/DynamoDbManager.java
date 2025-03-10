package org.example.data;


import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDbManager {

    private static final Region REGION = Region.EU_NORTH_1;

    public static DynamoDbClient connect() {
        return DynamoDbClient.builder()
                .region(REGION)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }
}
