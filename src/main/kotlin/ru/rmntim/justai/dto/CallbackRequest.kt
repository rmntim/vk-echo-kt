package ru.rmntim.justai.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = CallbackRequest.Confirmation::class, name = "confirmation"),
    JsonSubTypes.Type(value = CallbackRequest.NewMessage::class, name = "message_new"),
)
sealed class CallbackRequest {
    data class NewMessage(
        val groupId: UInt,
        val `object`: MessageNewObject
    ) : CallbackRequest()

    data class Confirmation(
        val groupId: UInt
    ) : CallbackRequest()
}