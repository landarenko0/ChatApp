package com.example.auth.service

import com.example.auth.entities.AuthUser
import com.example.auth.entities.UserApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthService {

    @POST("/api/auth")
    suspend fun login(@Body user: AuthUser): Response<UserApi>
}