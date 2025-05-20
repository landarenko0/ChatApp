package com.example.auth.repository

import android.content.Context
import com.example.auth.entities.AuthUser
import com.example.auth.entities.User
import com.example.auth.entities.UserApi
import com.example.auth.entities.mappers.toApi
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

    private val basePath = "${context.filesDir.absolutePath}/$AUTH_BASE_PATH"
    private val authFilePath = "$basePath/$AUTH_FILE_NAME"

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

    override fun checkUserLoggedIn(): Boolean = File(authFilePath).exists()

    override fun getSavedUser(): User? {
        return try {
            Json.decodeFromString<UserApi>(File(authFilePath).readText()).toDomain()
        } catch (_: Exception) {
            null
        }
    }

    override fun logOut() {
        File(authFilePath).delete()
    }

    override fun saveUser(user: User) {
        val authFile = File(authFilePath)

        if (!authFile.exists()) {
            File(basePath).mkdirs()
            authFile.createNewFile()
        }

        authFile.writeText(Json.encodeToString(user.toApi()))
    }

    companion object {
        private const val AUTH_BASE_PATH = "auth"
        private const val AUTH_FILE_NAME = "auth.txt"
    }
}