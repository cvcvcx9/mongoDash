package com.cvcvcx.MongoDash.config

import mu.KotlinLogging
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

@Configuration
@EnableConfigurationProperties(R2Properties::class)
class R2Config (
    private val props: R2Properties
){

    @Bean
    fun r2Client(): S3Client {

        val credentials = AwsBasicCredentials.create(
            props.accessKey,
            props.secretAccessKey
        )
        val serviceConfig = S3Configuration.builder()
            .pathStyleAccessEnabled(true)
//            .chunkedEncodingEnabled(false)
            .build()


        return S3Client.builder()
            .endpointOverride(URI.create(props.url))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(Region.of("auto"))
            .serviceConfiguration(serviceConfig)
            .build()


    }
}