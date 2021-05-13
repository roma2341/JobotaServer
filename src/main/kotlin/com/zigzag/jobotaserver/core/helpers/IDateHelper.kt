package com.zigzag.jobotaserver.core.helpers

import java.time.LocalDateTime

/**
 * Store date functions to improve testing experience
 * @author  Roman Zinchuk
 */
interface IDateHelper {
    fun now(): LocalDateTime;
}