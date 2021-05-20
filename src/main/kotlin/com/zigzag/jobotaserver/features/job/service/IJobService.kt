package com.zigzag.jobotaserver.features.job.service

import com.zigzag.jobotaserver.core.ICrudService
import com.zigzag.jobotaserver.features.job.database.PlatformJob
import reactor.core.publisher.Mono

/**
 *
 * @author  Roman Zinchuk
 */
interface IJobService : ICrudService<PlatformJob> {
    fun assignExecutor(jobId:String, executorUserId:String): Mono<PlatformJob>
    fun completeJob(jobId:String): Mono<PlatformJob>
}