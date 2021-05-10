package com.zigzag.jobotaserver.features.job.service

import com.zigzag.jobotaserver.core.ICrudService
import com.zigzag.jobotaserver.features.job.database.Job
import reactor.core.publisher.Mono

interface IJobService : ICrudService<Job> {
    fun assignExecutor(jobId:String, executorUserId:String): Mono<Job>;
    fun finishJobExecution(jobId:String): Mono<Job>;
    fun acceptJob(jobId:String): Mono<Job>;
}