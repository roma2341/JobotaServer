package com.zigzag.jobotaserver.migrations

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.springframework.data.mongodb.core.mapping.Document

@ChangeLog(order="001")
class ChangeLog001_Initial {
    @ChangeSet(order = "001", id = "createMyCollection", author = "zigzag")
    fun changeWithMongoDatabbase(db: MongoDatabase) {
         db.createCollection("users");
     //   val doc: Document = Document("testName", "example").append("test", "1")
        //mycollection.insertOne(doc)
    }
}