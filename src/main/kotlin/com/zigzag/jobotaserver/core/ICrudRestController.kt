package com.zigzag.jobotaserver.core

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
interface ICrudRestController<ModelDto,NewModelDto> {
    @GetMapping("/")
    fun all(): Flux<ModelDto>;

    @GetMapping("/{entityId}")
    fun get(@PathVariable entityId:String): Mono<ModelDto>;

    @PostMapping()
    fun create(entity: NewModelDto): Mono<ModelDto>;

    @DeleteMapping()
    fun delete(entityId: String): Mono<Void>;

}