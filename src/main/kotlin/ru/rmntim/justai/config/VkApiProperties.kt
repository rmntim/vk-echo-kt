package ru.rmntim.justai.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "vk.api")
data class VkApiProperties(
    val token: String,
    val version: String,
    val confirmationToken: String,
    val confirmationGroupId: UInt
)