package com.mongodb.crud;



import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.util.Helper.printJson;

public class FindWithProjectionTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithFilterTest");

        collection.drop();

        // insert 10 documents with two random integers
        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                                 .append("x", new Random().nextInt(2))
                                 .append("y", new Random().nextInt(100))
                                 .append("i", i));
        }

        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

//        Bson projection = new Document("y", 1).append("i", 1).append("_id", 0);
        Bson projection = fields(include("y", "i","x"), excludeId());

        List<Document> all = collection.find(filter)
                                       .projection(projection)
                                       .into(new ArrayList<Document>());

        for (Document cur : all) {
            printJson(cur);
        }
    }
}

