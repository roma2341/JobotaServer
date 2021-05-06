package com.zigzag.jobotaserver.features.job.database

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface JobRepository : ReactiveMongoRepository<Job,String>
