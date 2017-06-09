package com.mongodb.crud;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.util.Helper.printJson;

public class FindWithFilterTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithFilterTest");
        collection.deleteOne(eq("_id", 4));
        collection.drop();

        // insert 10 documents with two random integers
        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                                 .append("x", new Random().nextInt(2))
                                 .append("y", new Random().nextInt(100)));
        }

//        Bson filter = new Document("x", 0)
//        .append("y", new Document("$gt", 10).append("$lt", 90));

        Bson filter = or(eq("x", 0), gt("y", 10), lt("y", 90));

        List<Document> all = collection.find(filter).into(new ArrayList<Document>());

        for (Document cur : all) {
            printJson(cur);
        }

        long count = collection.count(filter);
        System.out.println();
        System.out.println(count);
    }
}
