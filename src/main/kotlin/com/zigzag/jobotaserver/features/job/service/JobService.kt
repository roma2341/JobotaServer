package com.zigzag.jobotaserver.features.job.service

import com.zigzag.jobotaserver.features.job.database.Job
import com.zigzag.jobotaserver.features.job.database.JobRepository
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuples

@Service
class JobService(val jobRepository: JobRepository,
val userRepository: PlatformUserRepository) : IJobService {

    override fun assignExecutor(jobId: String, executorUserId: String): Mono<Job> {
        return Mono.zip(jobRepository.findById(jobId),userRepository.findById(executorUserId), Tuples::of).map { tuple ->
            tuple.t1.executor = tuple.t2
            tuple.t1;
        };
    }

    override fun finishJobExecution(jobId:String): Mono<Job> {
        return jobRepository.findById(jobId).flatMap { job -> jobRepository.save(job) }
    }

    override fun acceptJob(jobId:String): Mono<Job> {
        return jobRepository.findById(jobId);
    }

    override fun all(): Flux<Job> {
        return jobRepository.findAll()
    }

    override fun get(modelId: String): Mono<Job> {
        return jobRepository.findById(modelId)
    }

    override fun create(model: Job): Mono<Job> {
        return jobRepository.save(model)
    }

    override fun delete(userId: String): Mono<Void> {
        return jobRepository.deleteById(userId)
    }
}