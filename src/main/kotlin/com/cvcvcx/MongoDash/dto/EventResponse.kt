package com.cvcvcx.MongoDash.dto

import ch.qos.logback.core.spi.ConfigurationEvent.EventType

data class EventResponse(
    val id: String?,
    val userId: String,
    val eventType: String,
    val page: String,
    val metadata: Map<String,Any>,
    val timestamp: String
)