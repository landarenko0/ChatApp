package com.example.auth.entities.mappers

import com.example.auth.entities.User
import com.example.auth.entities.UserApi

internal fun User.toApi(): UserApi = UserApi(
    id = this.id,
    name = this.name
)