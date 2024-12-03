package ru.rmntim.justai.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.rmntim.justai.config.VkApiProperties

@Service
class MessageService(
    private val vkApiProperties: VkApiProperties
) {
    fun sendMessage(userId: Int, message: String) {
        val restTemplate = RestTemplate()
        val requestParams = mapOf(
            "message" to message,
            "peer_id" to userId.toString(),
            "access_token" to vkApiProperties.token,
            "v" to vkApiProperties.version,
            "random_id" to "0"
        )
        val getParams = requestParams.map { "${it.key}=${it.value}" }.joinToString("&")
        val url = "https://api.vk.com/method/messages.send?$getParams"
        restTemplate.getForObject(url, String::class.java)
    }
}