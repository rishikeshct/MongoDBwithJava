package com.mongodb.crud;

import static java.util.Arrays.asList;

import org.bson.Document;
import static com.mongodb.util.Helper.printJson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class insertTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("insertTest");

        coll.drop();

        Document smith = new Document("name", "Smith")
                         .append("age", 30)
                         .append("profession", "programmer");

        Document jones = new Document("name", "Jones")
                         .append("age", 25)
                         .append("profession", "hacker");

        printJson(smith);

        coll.insertOne(smith);
        coll.insertOne(jones);

        printJson(smith);
        printJson(jones);

        coll.drop();

        coll.insertMany(asList(smith, jones));

        printJson(smith);
        printJson(jones);
        System.out.println(smith);
    }
}

