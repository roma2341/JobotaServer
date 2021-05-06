package com.zigzag.jobotaserver.job.controller;

import com.zigzag.jobotaserver.core.ICrudRestController
import com.zigzag.jobotaserver.job.database.JobRepository
import com.zigzag.jobotaserver.job.dto.JobDto
import com.zigzag.jobotaserver.job.dto.NewJobDto
import com.zigzag.jobotaserver.job.mapper.JobMapper
import com.zigzag.jobotaserver.job.mapper.NewJobMapper
import com.zigzag.jobotaserver.job.service.IJobService
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("job")
class JobController(val jobService: IJobService,
                    val jobRepository: JobRepository,
                    val jobMapper:JobMapper,
                    val newJobMapper:NewJobMapper) : ICrudRestController<JobDto,NewJobDto> {
    override fun all(): Flux<JobDto> {
        return jobService.all().map(jobMapper::convertToDto);
    }

    override fun get(entityId: String): Mono<JobDto> {
        return jobRepository.findById(entityId).map(jobMapper::convertToDto);
    }

    override fun create(entity: NewJobDto): Mono<JobDto> {
        return jobRepository.save(newJobMapper.convertToModel(entity))
            .map(jobMapper::convertToDto);
    }

    override fun delete(entityId: String): Mono<Void> {
        return jobRepository.deleteById(entityId);
    }

}
