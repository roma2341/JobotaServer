@file:Suppress("ClassName", "unused", "unused")

package com.zigzag.jobotaserver.migrations

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.IndexOptions

@ChangeLog(order="001")
class ChangeLog001_Initial {
    @ChangeSet(order = "001", id = "createMyCollection", author = "zigzag")
    fun changeWithMongoDatabase(db: MongoDatabase) {
         db.createCollection("users");
         db.getCollection("users").createIndex(BasicDBObject("email", 1), IndexOptions().unique(true));
        //   val doc: Document = Document("testName", "example").append("test", "1")
        //mycollection.insertOne(doc)
    }
}