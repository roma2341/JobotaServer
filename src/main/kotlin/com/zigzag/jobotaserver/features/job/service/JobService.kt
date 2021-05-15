package com.zigzag.jobotaserver.features.job.service

import com.zigzag.jobotaserver.core.helpers.IDateHelper
import com.zigzag.jobotaserver.features.job.database.PlatformJob
import com.zigzag.jobotaserver.features.job.database.JobRepository
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuples

/**
 *
 * @author  Roman Zinchuk
 */
@Service
class JobService(val jobRepository: JobRepository,
val userRepository: PlatformUserRepository,val dateHelper: IDateHelper) : IJobService {

    override fun assignExecutor(jobId: String, executorUserId: String): Mono<PlatformJob> {
        return Mono.zip(jobRepository.findById(jobId),userRepository.findById(executorUserId), Tuples::of).flatMap { tuple ->
            tuple.t1.executor = tuple.t2
            tuple.t1;
            jobRepository.save(tuple.t1);
        };
    }

    override fun completeJob(jobId:String): Mono<PlatformJob> {
        return jobRepository.findById(jobId).flatMap { job ->
            job.completedAt = dateHelper.now()
            jobRepository.save(job) }
    }

    override fun all(): Flux<PlatformJob> {
        return jobRepository.findAll()
    }

    override fun get(modelId: String): Mono<PlatformJob> {
        return jobRepository.findById(modelId)
    }

    override fun create(model: PlatformJob): Mono<PlatformJob> {
        return ReactiveSecurityContextHolder.getContext().flatMap{
            context -> context.authentication
            jobRepository.save(model)
        }
    }

    override fun delete(userId: String): Mono<Void> {
        return jobRepository.deleteById(userId)
    }
}