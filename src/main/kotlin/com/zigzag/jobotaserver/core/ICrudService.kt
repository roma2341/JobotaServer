package com.zigzag.jobotaserver.core

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
/**
 *
 * @author  Roman Zinchuk
 */
interface ICrudService<Model> {
    fun all(): Flux<Model>
    fun get(modelId:String): Mono<Model>
    fun create(model: Model): Mono<Model>
    fun delete(userId: String): Mono<Void>
}