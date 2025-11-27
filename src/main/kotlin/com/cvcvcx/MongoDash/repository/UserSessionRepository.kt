package com.cvcvcx.MongoDash.repository

import com.cvcvcx.MongoDash.entity.UserSession
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSessionRepository : MongoRepository<UserSession, String> {
    fun findBySessionId(sessionId: String): UserSession?
}
