package com.example.auth.usecases

import com.example.auth.entities.User
import com.example.auth.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val saveUserUseCase: SaveUserUseCase
) {

    suspend operator fun invoke(username: String): Result<User> {
        val result = repository.login(username)

        if (result.isSuccess) {
            result.getOrNull()?.let {
                saveUserUseCase(it)
            }
        }

        return result
    }
}