package com.zigzag.jobotaserver

import com.github.cloudyrock.spring.v5.EnableMongock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing

/**
 *
 * @author  Roman Zinchuk
 */
@SpringBootApplication
@EnableReactiveMongoAuditing
@EnableMongock
class JobotaServerApplication

fun main(args:Array<String>) {
    runApplication<JobotaServerApplication>(*args)
}

