package hassidi.couchbase;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        Cluster cluster = CouchbaseCluster.create("tlvciapi.tlv.lpnet.com");
//        cluster.authenticate("Administrator", "liveperson");
        final Bucket bucket = cluster.openBucket("ams_brands", "ams");

        // Create a JSON Document
        JsonObject arthur = JsonObject.create()
                .put("name", "Arthur")
                .put("email", "kingarthur@couchbase.com");

        // Store the Document
        bucket.upsert(JsonDocument.create("u:king_arthur", 20,  arthur));

//        final JsonDocument jdoc1 = bucket.get("u:king_arthur");
//        bucket.upsert(jdoc1);

        for (int i = 0; i < 50; i++) {
            // Load the Document and print it
            // Prints Content and Metadata of the stored Document
            final JsonDocument jdoc = bucket.get("u:king_arthur");
            System.out.println("Doc: " + jdoc);
            TimeUnit.SECONDS.sleep(5L);

            ViewResult result = bucket.query(ViewQuery.from("test", "test"), 3, TimeUnit.SECONDS);
            for (ViewRow row : result) {
                System.out.println("Row: " + row); //prints the row
//                System.out.println(row.document().content()); //retrieves the doc and prints content
            }

            if (jdoc == null) {
                System.out.println("jdoc is null");
                break;
            }
        }

    }
}
