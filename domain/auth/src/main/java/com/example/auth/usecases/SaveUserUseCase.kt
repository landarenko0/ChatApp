package com.example.auth.usecases

import com.example.auth.entities.User
import com.example.auth.repository.AuthRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(private val repository: AuthRepository) {

    operator fun invoke(user: User) {
        repository.saveUser(user)
    }
}