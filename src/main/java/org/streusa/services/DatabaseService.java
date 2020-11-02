package org.streusa.services;


import java.util.Map.Entry;
import java.util.Set;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.org.lightcouch.CouchDbException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;

public class DatabaseService {


    private static CloudantClient cloudant = null;
    private static Database db = null;

    /*@Value("${DATABASE_APIKEY}")
    private String apiKeyNS;
    //private static String apiKey = null;

    @Value("${DATABASE_USER}")
    private String userNS;

    @Value("${DATABASE_INSTANCE}")
    private String dbInstanceNS;*/
    //private static String user = "8eb342de-b85b-42ab-a5f3-71b85ba78320-bluemix";
//	private static String password = "9718258bf66567dc84746d2aa52156653e2ec4a683495052f71b4612d4cb47a6";

    private static String apiKey = "1ktSJAUmkeAks6b2WQeD-O0tH1nCqyXujzLpYihT9y1R";
    private static String user = "9981a720-802f-474b-8fd5-058819d07029-bluemix";
    private static String dbInstance = "Cloudant-4k";



    private static void initClient(String dbInstance) {

        if (cloudant == null) {
            synchronized (DatabaseService.class) {
                if (cloudant != null) {
                    return;
                }
                cloudant = createClient(dbInstance);

            }
        }
    }

    private static CloudantClient createClient(String dbInstance) {
        /*String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
        String serviceName = null;

        if (VCAP_SERVICES != null) {
            // When running in Bluemix, the VCAP_SERVICES env var will have the credentials
            // for all bound/connected services
            // Parse the VCAP JSON structure looking for cloudant.
            JsonObject obj = (JsonObject) new JsonParser().parse(VCAP_SERVICES);
            JsonArray dbs = new JsonArray();
            Entry<String, JsonElement> dbEntry = null;
            Set<Entry<String, JsonElement>> entries = obj.entrySet();
            // Look for the VCAP key that holds the cloudant no sql db information
            for (Entry<String, JsonElement> eachEntry : entries) {
                if (eachEntry.getKey().toLowerCase().contains("cloudant")) {
                    dbEntry = eachEntry;
                    break;
                }
            }
            if (dbEntry == null) {
                throw new RuntimeException("Could not find cloudantNoSQLDB key in VCAP_SERVICES env variable");
            }

            dbs = (JsonArray) dbEntry.getValue();

            System.out.println("********************");

            System.out.println(dbs.toString());

            System.out.println("********************");

            for(JsonElement jsnElem : dbs) {
                if(jsnElem.getAsJsonObject().get("instance_name").getAsString().equalsIgnoreCase(dbInstance)) {
                    obj = jsnElem.getAsJsonObject();
//					apiKey = jsnElem.getAsJsonObject().get("credentials").getAsJsonObject().get("apikey").getAsString();
                    apiKey = "IUv8nRjDW5j44SO-TWKrRBxfeFTcL-nFc23n2WjvczX4";
                }

            }

            serviceName = (String) dbEntry.getKey();
            System.out.println("Service Name - " + serviceName);

            user = obj.get("credentials").getAsJsonObject().get("username").getAsString();


//			password = obj.get("password").getAsString();

        } else {
            System.out.println("VCAP_SERVICES env var doesn't exist: trying default apikey...");
            apiKey = "IUv8nRjDW5j44SO-TWKrRBxfeFTcL-nFc23n2WjvczX4";
        }*/

        try {

            System.out.println("Connecting to Cloudant : " + user);
            CloudantClient client = ClientBuilder.account(user).iamApiKey(apiKey).build();
            return client;

        } catch (CouchDbException e) {

            throw new RuntimeException("Unable to connect to repository", e);

        }
    }

    public static Database getDB(String dbInstance, String dbName) {

        System.out.println("Request for Cloudant db: " + dbName+" from instance: "+dbInstance);
        if (cloudant == null) {
            initClient(dbInstance);
        }
        try {
            db = cloudant.database(dbName, true);
        } catch (Exception e) {
            throw new RuntimeException("DB Not found", e);
        }

        return db;
    }
/*
    public static Database usersDB = getDB("Cloudant-streusa20", "users");
    public static Database booksDB = getDB("Cloudant-streusa20", "books");
    public static Database merchandisesDB = getDB("Cloudant-streusa20", "merchandises");
    public static Database editorsDB = getDB("Cloudant-streusa20", "editors");
    public static Database deadlinesDB = getDB("Cloudant-streusa20", "deadlines");
    public static Database genresDB = getDB("Cloudant-streusa20", "genres");
    public static Database purchasesDB = getDB("Cloudant-streusa20", "purchases");
    public static Database suppliersDB = getDB("Cloudant-streusa20", "suppliers");
*/

    public static Database usersDB = getDB(dbInstance, "users");
    public static Database booksDB = getDB(dbInstance, "books");
    public static Database merchandisesDB = getDB(dbInstance, "merchandises");
    public static Database editorsDB = getDB(dbInstance, "editors");
    public static Database deadlinesDB = getDB(dbInstance, "deadlines");
    public static Database genresDB = getDB(dbInstance, "genres");
    public static Database purchasesDB = getDB(dbInstance, "purchases");
    public static Database suppliersDB = getDB(dbInstance, "suppliers");
}

