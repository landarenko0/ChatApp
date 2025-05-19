package com.example.auth.entities.mappers

import com.example.auth.entities.User
import com.example.auth.entities.UserApi

internal fun UserApi.toDomain(): User = User(
    id = this.id,
    name = this.name
)