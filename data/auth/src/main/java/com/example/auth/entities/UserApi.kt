package com.example.auth.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserApi(
    @SerialName("id") val id: Long,
    @SerialName("user_name") val name: String
)