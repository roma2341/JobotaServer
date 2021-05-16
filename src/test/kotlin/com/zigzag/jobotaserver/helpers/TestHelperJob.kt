package com.zigzag.jobotaserver.helpers

import com.zigzag.jobotaserver.features.job.database.PlatformJob
import org.springframework.stereotype.Component

@Component
class TestHelperJob {
    fun createTestJob():PlatformJob{
        return PlatformJob(name="test-job",description = "test description");
    }
}