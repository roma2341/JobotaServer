package com.zigzag.jobotaserver.job.service

import com.zigzag.jobotaserver.job.database.Job
import com.zigzag.jobotaserver.job.database.JobRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class JobService(val jobRepository: JobRepository) : IJobService {

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