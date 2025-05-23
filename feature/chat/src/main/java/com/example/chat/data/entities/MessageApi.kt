package com.example.chat.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageApi(
    @SerialName("user_id") val userId: Long,
    @SerialName("user_name") val username: String,
    @SerialName("message") val message: String
)
