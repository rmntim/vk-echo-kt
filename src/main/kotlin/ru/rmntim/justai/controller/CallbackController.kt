package ru.rmntim.justai.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import ru.rmntim.justai.dto.CallbackRequest

@RestController
@RequestMapping("/callback")
class CallbackController {
    private val log = LoggerFactory.getLogger(CallbackController::class.java)
    private val confirmationToken = "bd82a4ba"
    private val confirmationGroupId = 228511457u
    private val apiVersion = "5.199"
    private val token =
        "vk1.a.ZNkf2poRwlUKnyjQ54jLTx0yKP_P1e7-Yce_ttr8k-pA5aWmE1HsIMBlqUYBLkh7wBHtvSYUFiTsL7TTzvCkN7GSOr8rzf4z3Pl6ZAImoAbElqKiKJPXhONp0tiuj9iHU9KjSXqiHhf3SAlS0oruRK6sb11lSCteFelbMMqJ285_d3ZKTdFGgw6eiCV7pQa9THmJAZpAeWfeikViaWsbOw"

    @PostMapping("/justai")
    fun handleCallback(@RequestBody data: CallbackRequest): ResponseEntity<String> {
        when (data) {
            is CallbackRequest.Confirmation -> {
                return when (data.groupId) {
                    confirmationGroupId -> ResponseEntity.ok(confirmationToken)
                    else -> ResponseEntity.badRequest().build()
                }
            }

            is CallbackRequest.NewMessage -> {
                log.info("New message {}", data.`object`)
                val message = data.`object`.message
                sendMessage(message.fromId, "Вы сказали: ${message.text}")
                return ResponseEntity.ok("ok")
            }
        }
    }


    private fun sendMessage(userId: Int, message: String) {
        val restTemplate = RestTemplate()
        val requestParams = mapOf(
            "message" to message,
            "peer_id" to userId.toString(),
            "access_token" to token,
            "v" to apiVersion,
            "random_id" to "0"
        )
        val getParams = requestParams.map { "${it.key}=${it.value}" }.joinToString("&")
        val url = "https://api.vk.com/method/messages.send?$getParams"
        restTemplate.getForObject(url, String::class.java)
    }
}