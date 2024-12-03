package ru.rmntim.justai

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.rmntim.justai.config.VkApiProperties

@SpringBootApplication
@EnableConfigurationProperties(VkApiProperties::class)
class JustaiApplication

fun main(args: Array<String>) {
    runApplication<JustaiApplication>(*args)
}
