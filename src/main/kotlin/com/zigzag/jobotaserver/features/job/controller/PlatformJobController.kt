package com.zigzag.jobotaserver.features.job.controller

import com.zigzag.jobotaserver.core.ICrudRestController
import com.zigzag.jobotaserver.features.job.database.JobRepository
import com.zigzag.jobotaserver.features.job.dto.NewPlatformJobDto
import com.zigzag.jobotaserver.features.job.dto.PlatformJobDto
import com.zigzag.jobotaserver.features.job.mapper.JobMapper
import com.zigzag.jobotaserver.features.job.mapper.NewJobMapper
import com.zigzag.jobotaserver.features.job.service.IJobService
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * @author  Roman Zinchuk
 */
@RestController
@RequestMapping("job")
class PlatformJobController(
    val jobService: IJobService,
    val jobRepository: JobRepository,
    val jobMapper: JobMapper,
    val newJobMapper: NewJobMapper,
                            ) : ICrudRestController<PlatformJobDto,NewPlatformJobDto> {

    fun assignExecutor(jobId:String,executorUserId: String):Mono<PlatformJobDto>{
        return jobService.assignExecutor(jobId,executorUserId).map(jobMapper::convertToDto)
    }

    override fun all(): Flux<PlatformJobDto> {
        return jobService.all().map(jobMapper::convertToDto)
    }

    override fun get(entityId: String): Mono<PlatformJobDto> {
        return jobRepository.findById(entityId).map(jobMapper::convertToDto)
    }

    override fun create(dto: NewPlatformJobDto): Mono<PlatformJobDto> {
        val entity = newJobMapper.convertToModel(dto)
        return jobService.create(entity).map{
            jobMapper.convertToDto(it)
        }
    }

    override fun delete(entityId: String): Mono<Void> {
        return jobRepository.deleteById(entityId)
    }

}
