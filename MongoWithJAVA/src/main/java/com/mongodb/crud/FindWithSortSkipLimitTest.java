package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.util.Helper.printJson;

public class FindWithSortSkipLimitTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithSortTest");

        collection.drop();

        // insert 100 documents with two random integers
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                collection.insertOne(new Document().append("i", i).append("j", j));
            }
        }

        Bson projection = fields(include("i", "j"), excludeId());
        Bson sort = descending("j", "i");

        List<Document> all = collection.find()
                                       .projection(projection)
                                       .sort(sort)
                                       .skip(20)
                                       .limit(50)
                                       .into(new ArrayList<Document>());

        for (Document cur : all) {
            printJson(cur);
        }
    }
}

