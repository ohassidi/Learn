package hassidi.couchbase;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

/**
 * Hello world!
 *
 */
public class CouchbaseReconnectApp
{
    public static void main( String[] args ) throws InterruptedException {
        Cluster cluster = CouchbaseCluster.create("tlvciapi.tlv.lpnet.com");
        final Bucket bucket = cluster.openBucket("ams_brands", "ams");

        JsonObject arthur = JsonObject.create()
                .put("name", "Arthur")
                .put("email", "kingarthur@couchbase.com");

        bucket.upsert(JsonDocument.create("u:king_arthur", arthur));

        final JsonDocument jdoc1 = bucket.get("u:king_arthur");
        assert jdoc1 != null;

        System.out.println(jdoc1);

        System.out.println("Disconnecting couchbase ++++");
        bucket.close();
        cluster.disconnect();

        System.out.println("Reconnecting couchbase ++++");
        cluster = CouchbaseCluster.create("qtvr-dbc0001.tlv.lpnet.com");
        Bucket bucket1 = cluster.openBucket("ams_brands", "ams");

        final JsonDocument jdoc2 = bucket1.get("u:king_arthur", 5, TimeUnit.SECONDS);
        System.out.println(jdoc2);
    }
}
