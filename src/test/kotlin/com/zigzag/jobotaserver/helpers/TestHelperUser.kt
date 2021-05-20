package com.zigzag.jobotaserver.helpers

import com.zigzag.jobotaserver.features.user.database.PlatformUser
import org.springframework.stereotype.Component

@Component
class TestHelperUser {
    companion object Options {
        const val CURRENT_USER_ID = "12345"
    }

    fun createTestUser():PlatformUser{
        return PlatformUser(
            firstName = "first-name",
            lastName = "last-name",
            email="email@org.com",
            password = "pass"
        )
    }
    fun createCurrentUser():PlatformUser{
        return PlatformUser(
            id=CURRENT_USER_ID,
            firstName = "first-name",
            lastName = "last-name",
            email="test-current-user@org.com",
            password = "pass"
        )
    }
    fun createTestUsersList(n:Int):List<PlatformUser>{
        return List(n){
            PlatformUser(
                firstName = "first-name${it}",
                lastName = "last-name${it}",
                email="email@org.com${it}",
                password = "pass${it}"
            )
        }
    }
}