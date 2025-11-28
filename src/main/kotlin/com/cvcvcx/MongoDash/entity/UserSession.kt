package com.cvcvcx.MongoDash.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "user_sessions")
open class UserSession(
    @Id
    val id: String? = null,

    @Field("session_id")
    val sessionId: String, // 프론트에서 생성한 UUID 또는 쿠키 ID

    @Field("user_id")
    val userId: String? = null, // 로그인한 경우만 저장

    val referrer: String? = null, // 유입 경로 (전체 URL)
    
    @Field("referrer_domain")
    val referrerDomain: String? = null, // 유입 도메인 (예: naver.com)

    @Field("landing_url")
    val landingUrl: String, // 처음 도착한 페이지

    @Field("device_type")
    val deviceType: String, // mobile, tablet, desktop

    @Field("user_agent")
    val userAgent: String,

    @Field("viewport_width")
    val viewportWidth: Int, // 브라우저 가로 크기 (반응형 구분용)

    @Field("viewport_height")
    val viewportHeight: Int,

    @Field("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
