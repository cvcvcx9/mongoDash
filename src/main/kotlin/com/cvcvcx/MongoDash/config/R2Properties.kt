package com.cvcvcx.MongoDash.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "cloudflare.r2.secret")
data class R2Properties (
    val url: String,
    val accessKey: String,
    val secretAccessKey: String,
    val bucket: String
)