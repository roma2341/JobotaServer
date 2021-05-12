package com.zigzag.jobotaserver.core.helpers

import java.time.LocalDateTime

interface IDateHelper {
    fun now(): LocalDateTime;
}