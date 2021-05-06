package com.zigzag.jobotaserver.core

import org.springframework.web.bind.annotation.PathVariable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ICrudService<Model> {
    fun all(): Flux<Model>;
    fun get(modelId:String): Mono<Model>;
    fun create(model: Model): Mono<Model>;
    fun delete(userId: String): Mono<Void>;
}