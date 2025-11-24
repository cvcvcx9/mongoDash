package com.cvcvcx.MongoDash.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant


@Document(collection = "events")
data class Event(
    @Id val id: String? = null,
    val userId: String,
    val eventType: String,
    val page: String,
    val metadata: Map<String,Any> = emptyMap(),
    val timestamp: Instant = Instant.now()
)