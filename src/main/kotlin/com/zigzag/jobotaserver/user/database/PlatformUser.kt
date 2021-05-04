package com.zigzag.jobotaserver.user.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
data class PlatformUser (
    @Id
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val email:String?
)
