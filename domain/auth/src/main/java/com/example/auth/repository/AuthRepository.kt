package com.example.auth.repository

import com.example.auth.entities.User

interface AuthRepository {

    suspend fun login(username: String): Result<User>

    fun checkUserLoggedIn(): Boolean

    fun saveUser(user: User)

    fun getSavedUser(): User?

    fun logOut()
}