package com.zigzag.jobotaserver.features.job.database

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author  Roman Zinchuk
 */
@Repository
interface JobRepository : ReactiveMongoRepository<PlatformJob,String>
