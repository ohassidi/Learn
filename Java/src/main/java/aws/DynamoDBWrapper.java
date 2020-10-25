package aws;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;

public class DynamoDBWrapper {

    public static void main(String[] args) {

        String tableName = "p360-event-cost-test";
        String primaryPartitionKeyName = "app_id";
        String partitionKeyName = "appid";
        String partitionKeyVal = "another-app";
        String partitionAlias = "#a";

        System.out.format("Querying %s", tableName);
        System.out.println();

        Region region = Region.EU_WEST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        System.out.println("Table is: " + getTableStatus(ddb, tableName));

        int count = queryTable(ddb, tableName, primaryPartitionKeyName, partitionKeyName, partitionKeyVal,partitionAlias ) ;
        System.out.println("Found "+count + " record(s) in table");
    }

    public static String getTableStatus (DynamoDbClient ddb, String tableName) {
        DescribeTableRequest request = DescribeTableRequest.builder()
                .tableName(tableName)
                .build();

        TableDescription tableDescription = ddb.describeTable(request).table();
        return tableDescription.tableStatusAsString();
    }

    public static int queryTable(DynamoDbClient ddb,
                                 String tableName,
                                 String primaryPartitionKeyName,
                                 String partitionKeyName,
                                 String partitionKeyVal,
                                 String partitionAlias) {

        // Set up an alias for the partition key name in case it's a reserved word
        HashMap<String,String> attrNameAlias = new HashMap<>();

        attrNameAlias.put(partitionAlias, primaryPartitionKeyName);

        // Set up mapping of the partition name with the value
        HashMap<String, AttributeValue> attrValues =
                new HashMap<>();
        AttributeValue attributeValue = AttributeValue.builder().s(partitionKeyVal).build();
        attrValues.put(":"+partitionKeyName, attributeValue);

//        Integer i = Integer.valueOf(AttributeValue.builder().n("1").build().n());

        // Create a QueryRequest object
        QueryRequest queryReq = QueryRequest.builder()
                .tableName(tableName)
                .keyConditionExpression(partitionAlias + " = :" + partitionKeyName)
                .expressionAttributeNames(attrNameAlias)
                .expressionAttributeValues(attrValues)
                .build();
        try {
            QueryResponse response = ddb.query(queryReq);
            return response.count();
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return -1;
    }
}
