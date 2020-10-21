import com.couchbase.client.java.*;
import com.couchbase.client.java.query.*;
import rx.Observable;

import static com.couchbase.client.java.query.Select.*;
import static com.couchbase.client.java.query.dsl.Expression.*;

/**
 * Created by David on 28/09/2014.
 */
public class Query {
    public static void main(String[] args) {
        System.setProperty("com.couchbase.client.queryEnabled", "true");

        Cluster cluster = new CouchbaseCluster();
        Bucket bucket = cluster.openBucket("default").toBlocking().single();

        Observable<QueryResult> results = bucket.query(
                "SELECT * FROM default"
        );

//        Observable<QueryResult> results = bucket.query(
//                select("*").from("default").where(x("age").between(x(33).and(x(36))))
//        );

        results.subscribe(result ->
                        System.out.println(result.value().getString("name") + " - " + result.value().getString("date"))
        );

        cluster.disconnect();
    }
}
