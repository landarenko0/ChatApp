package com.example.auth.repository

import android.content.Context
import com.example.auth.entities.AuthUser
import com.example.auth.entities.User
import com.example.auth.entities.UserApi
import com.example.auth.entities.mappers.toDomain
import com.example.auth.exceptions.ApiException
import com.example.auth.service.AuthService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val service: AuthService
) : AuthRepository {

    private val authFile = File("${context.filesDir.absolutePath}/auth/auth.txt")

    override suspend fun login(username: String): Result<User> {
        return try {
            val result = service.login(AuthUser(username))
            val body = result.body()

            if (result.isSuccessful && body is UserApi) {
                Result.success(body.toDomain())
            } else {
                Result.failure(ApiException(result.errorBody()?.string()))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override fun checkUserLoggedIn(): Boolean = authFile.exists()

    override fun getSavedUser(): User? {
        return try {
            Json.decodeFromString(authFile.readText())
        } catch (_: Exception) {
            null
        }
    }

    override fun logOut() {
        authFile.delete()
    }

    override fun saveUser(user: User) {
        if (!authFile.exists()) {
            authFile.createNewFile()
        }

        authFile.writeText(Json.encodeToString(user))
    }
}