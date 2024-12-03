package ru.rmntim.justai.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rmntim.justai.config.VkApiProperties
import ru.rmntim.justai.dto.CallbackRequest
import ru.rmntim.justai.service.MessageService

@RestController
@RequestMapping("/callback")
class CallbackController(
    private val messageService: MessageService,
    private val vkApiProperties: VkApiProperties
) {
    private val log = LoggerFactory.getLogger(CallbackController::class.java)

    @PostMapping("/justai")
    fun handleCallback(@RequestBody data: CallbackRequest): ResponseEntity<String> {
        when (data) {
            is CallbackRequest.Confirmation -> {
                return when (data.groupId) {
                    vkApiProperties.confirmationGroupId -> ResponseEntity.ok(vkApiProperties.confirmationToken)
                    else -> ResponseEntity.badRequest().build()
                }
            }

            is CallbackRequest.NewMessage -> {
                log.info("New message {}", data.`object`)
                val message = data.`object`.message
                messageService.sendMessage(message.fromId, "Вы сказали: ${message.text}")
                return ResponseEntity.ok("ok")
            }
        }
    }
}