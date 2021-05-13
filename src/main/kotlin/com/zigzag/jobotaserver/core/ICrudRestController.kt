package com.zigzag.jobotaserver.core

import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * @author  Roman Zinchuk
 */
@RestController
interface ICrudRestController<ModelDto,NewModelDto> {
    @GetMapping("/")
    fun all(): Flux<ModelDto>

    @GetMapping("/{entityId}")
    fun get(@PathVariable entityId:String): Mono<ModelDto>

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(entity: NewModelDto): Mono<ModelDto>

    @DeleteMapping
    fun delete(entityId: String): Mono<Void>

    @ExceptionHandler
    fun handleDuplicateKeyException(ex: DuplicateKeyException?): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.CONFLICT).build<Any>()
    }

}