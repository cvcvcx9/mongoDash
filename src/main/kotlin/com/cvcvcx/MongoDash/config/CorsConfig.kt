package com.cvcvcx.MongoDash.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()

        config.allowedOriginPatterns = listOf(
            "https://cvcvcx9.org",
            "https://*.cvcvcx9.org",
            "http://localhost:3000"
        )
        config.allowedMethods = listOf("GET","POST","PUT","DELETE","OPTIONS")
        config.allowedHeaders = listOf("*")
        config.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**",config)
        return source

    }

    @Bean
    fun corsFilter(): CorsFilter{
        return CorsFilter(corsConfigurationSource())
    }

}