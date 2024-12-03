package ru.rmntim.justai.dto

data class Message(
    val date: Long,
    val fromId: Int,
    val id: Int,
    val version: Int,
    val out: Int,
    val important: Boolean,
    val isHidden: Boolean,
    val attachments: List<Any>,
    val conversationMessageId: Int,
    val fwdMessages: List<Any>,
    val text: String,
    val peerId: Int,
    val randomId: Int
)

