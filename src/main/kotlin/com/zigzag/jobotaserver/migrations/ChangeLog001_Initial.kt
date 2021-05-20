@file:Suppress("ClassName", "unused", "unused")

package com.zigzag.jobotaserver.migrations

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.zigzag.jobotaserver.features.user.database.PlatformUser
import com.zigzag.jobotaserver.features.user.service.IPlatformUserService


/**
 *Deprecated
 * @author  Roman Zinchuk
 */
@ChangeLog(order="001")
class ChangeLog001_Initial {
    @ChangeSet(order = "001", id = "createMyCollection", author = "zigzag")
    fun initTestUsers( userService: IPlatformUserService){
        val testUser = PlatformUser(email="t@t.t",password = "p",roles = listOf("admin","test"))
        userService.create(testUser).block()
    }
    /*@ChangeSet(order = "001", id = "createMyCollection", author = "zigzag")
    fun changeWithMongoDatabase(db: MongoDatabase) {
         db.createCollection("users");
         db.getCollection("users").createIndex(BasicDBObject("email", 1), IndexOptions().unique(true));

        val schema = MongoJsonSchema.builder()
            .required("email")
            .properties(
                JsonSchemaProperty.string("email"),
                JsonSchemaProperty.string("firstName"),
                JsonSchemaProperty.string("lastName"),
            )
            .build()

        db.createCollection("jobs");
        db.getCollection("job").
        //   val doc: Document = Document("testName", "example").append("test", "1")
        //mycollection.insertOne(doc)
    }*/
}