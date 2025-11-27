package com.cvcvcx.MongoDash.dto

import com.cvcvcx.MongoDash.entity.UserSession

data class UserSessionDto(
    val sessionId: String,
    val userId: String? = null,
    val referrer: String? = null,
    val referrerDomain: String? = null,
    val landingUrl: String,
    val deviceType: String,
    val userAgent: String,
    val viewportWidth: Int,
    val viewportHeight: Int
) {
    fun toEntity(): UserSession {
        return UserSession(
            sessionId = this.sessionId,
            userId = this.userId,
            referrer = this.referrer,
            referrerDomain = this.referrerDomain,
            landingUrl = this.landingUrl,
            deviceType = this.deviceType,
            userAgent = this.userAgent,
            viewportWidth = this.viewportWidth,
            viewportHeight = this.viewportHeight
        )
    }
}
